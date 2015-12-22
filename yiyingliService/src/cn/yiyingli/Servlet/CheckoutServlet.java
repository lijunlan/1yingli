package cn.yiyingli.Servlet;

/*==================================================================
 PayPal Express Check out Call
 ===================================================================
*/

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.ExchangeData.ExServiceProCopy;
import cn.yiyingli.ExchangeData.ExTeacherCopy;
import cn.yiyingli.PayPal.PayPal;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class CheckoutServlet extends HttpServlet {

	private static String page = "http://www.1yingli.cn/#!/myTutor";

	// private static final String testPage =
	// "http://testweb.1yingli.cn/yourTutor.html";

	private static final String resultParameter = "?paymentResult=";

	// 当payapl链接失败返回的网页
	private static final String connectError = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">    <title>你的导师</title>    <link rel=\"Shortcut Icon\" href=\"http://image.1yingli.cn/img/logo0.png\">    <link rel=\"Bookmark\" href=\"http://image.1yingli.cn/img/logo0.png\">    <style type=\"text/css\">    	#succ{    		width: 400px;height: 200px;margin: auto;position: fixed;top: 50%;left: 50%;margin-top:-150px;margin-left:-200px;border-radius: 10px;z-index: 101;background: #fff; border:1px solid #D2D2D2    	}    	.succ_title{    	  width: 400px;height: 35px;background-color: #d2d2d2;border-top-left-radius: 10px;border-top-right-radius: 10px;text-align: center;    	}    	.succ_title div{    	  font-size: 16px;padding-top: 5px;font-weight: bold;color:#FFF;    	}    	.succ_content{    		position: absolute;top: 40%;left: 33%;font-size: 20px;color: #b6b6b6;    	}    	#succ a{    		text-decoration: none;width: 128px;position: absolute;top: 70%;left: 35%;font-size: 20px;color: #FFF;background-color: #56bbe8;text-align: center;border-radius: 14px;    	}    </style></head><body>	<div id=\"succ\">    	<div class=\"succ_title\"><div>来自一英里的信息</div></div>    	<div class=\"succ_content\">支付连接超时，请重新支付。</div>    	<a href=\"http://www.1yingli.cn/#!/myTutor\">确定</a>		</div></body>";

	private ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PayPal paypal = new PayPal();
		// Paypal调用的returnServlet
		String returnURL = "http://service.1yingli.cn/yiyingliService/Return?page=return";
		// 当取消交易的时候，返回地址
		String cancelURL = page;
		Map<String, String> checkoutDetails = new HashMap<String, String>();
		// 检查前台传来的数据
		if (request.getParameter("oid") == null || request.getParameter("uid") == null) {
			returnMsg(response, MsgUtil.getErrorMsgByCode("00001"));
			return;
		}
		UserMarkService userMarkService = (UserMarkService) getApplicationContext().getBean("userMarkService");
		OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");
		LogUtil.info(
				"receive>>>>PAYPAL orderId:" + request.getParameter("oid") + "\t uid:" + request.getParameter("uid"),
				this.getClass());
		// 商户网站订单系统中唯一订单号，必填
		Order order = orderService.queryByShowId(request.getParameter("oid"), false);
		if (order == null) {
			returnMsg(response, MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		// 用户id
		String uid = request.getParameter("uid");
		User user = userMarkService.queryUser(uid);
		if (user == null) {
			returnMsg(response, MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>ORDER IS NOT BELONG TO YOU createOrderId:" + order.getCreateUser().getId()
					+ ",userId:" + user.getId() + "," + (order.getCreateUser().getId() == user.getId()),
					this.getClass());
			returnMsg(response, MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = order.getState();
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			returnMsg(response, MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		// 由后台插入相关数据
		checkoutDetails.put("L_PAYMENTREQUEST_0_NAME0", URLEncoder.encode(
				"【一英里】[" + ExTeacherCopy.getTeacherName(order) + "]" + ExServiceProCopy.getServiceProMultiTitle(order),
				"UTF-8"));
		// 货物id，这里填写的是订单号
		checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", order.getOrderNo());

		// 订单名称
		checkoutDetails.put("L_PAYMENTREQUEST_0_DESC0", URLEncoder.encode(
				"【一英里】[" + ExTeacherCopy.getTeacherName(order) + "]" + ExServiceProCopy.getServiceProMultiTitle(order),
				"UTF-8"));
		checkoutDetails.put("L_PAYMENTREQUEST_0_QTY0", "1");

		// 商品价格
		float price = order.getMoney();
		price /= 6;
		BigDecimal b = new BigDecimal(price);
		price = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		if (price < 0.01)
			price = (float) 0.01;

		checkoutDetails.put("PAYMENTREQUEST_0_ITEMAMT", price + "");
		checkoutDetails.put("PAYMENTREQUEST_0_HANDLINGAMT", "0");
		// 包括税款，手续费（这些我们都是零）的总金额
		checkoutDetails.put("PAYMENTREQUEST_0_AMT", price + "");
		// 我们的订单号,以及微信端回调页面（可选）
		if (request.getParameter("callback") == null) {
			checkoutDetails.put("PAYMENTREQUEST_0_CUSTOM", request.getParameter("oid"));
			page = "http://www.1yingli.cn/#!/myTutor";
		} else {
			checkoutDetails.put("PAYMENTREQUEST_0_CUSTOM",
					request.getParameter("oid") + "|" + request.getParameter("callback"));
			page = request.getParameter("callback");
		}
		checkoutDetails.put("REQCONFIRMSHIPPING", "0");
		checkoutDetails.put("NOSHIPPING", "1");

		checkoutDetails.put("PAYMENTREQUEST_0_CURRENCYCODE", "USD");
		checkoutDetails.put("PAYMENTREQUEST_0_PAYMENTACTION", "Sale");

		Map<String, String> nvp = paypal.callShortcutExpressCheckout(checkoutDetails, returnURL, cancelURL);
		if (nvp == null) {
			returnMsg(response, connectError);
			return;
		}
		String strAck = nvp.get("ACK").toString().toUpperCase();
		if (strAck != null && (strAck.equals("SUCCESS") || strAck.equals("SUCCESSWITHWARNING"))) {
			// Redirect to paypal.com
			paypal.redirectURL(response, nvp.get("TOKEN").toString(),
					paypal.getUserActionFlag().equalsIgnoreCase("true"));
		} else {
			// log error information returned by PayPal
			String ErrorCode = nvp.get("L_ERRORCODE0").toString();
			String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
			String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
			String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();

			String errorString = "SetExpressCheckout API call failed. " + "Detailed Error Message: " + ErrorLongMsg
					+ "Short Error Message: " + ErrorShortMsg + "Error Code: " + ErrorCode + "Error Severity Code: "
					+ ErrorSeverityCode;
			LogUtil.error("After SetExpressCheckoutDetails from Paypal and ERROR INFO:" + errorString, this.getClass());
			returnToOnemile(resultParameter + "fail", response);

		}
	}

	@SuppressWarnings("unused")
	private Map<String, String> setRequestParams(HttpServletRequest request) {
		Map<String, String> requestMap = new HashMap<String, String>();
		for (String key : request.getParameterMap().keySet()) {
			requestMap.put(key, StringEscapeUtils.escapeHtml4(request.getParameterMap().get(key)[0]));
		}

		return requestMap;

	}

	public void returnToOnemile(String para, HttpServletResponse response) {
		try {
			response.sendRedirect(page + para);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * return message through httpResponse
	 * 
	 * @param msg
	 */
	private void returnMsg(HttpServletResponse resp, String msg) {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		try {
			OutputStream stream = resp.getOutputStream();
			LogUtil.info("send>>>>>" + msg, this.getClass());
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}