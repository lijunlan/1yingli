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
import javax.servlet.http.HttpSession;
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

public class CheckoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2722761580200224133L;

	//@SuppressWarnings("unused")
	//private static final String page = "http://www.1yingli.cn/yourTutor.html";
	
	private static final String testPage = "http://www.1yingli.cn/yourTutor.html";
	
	private static final String resultParameter = "?paymentResult=";

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
		HttpSession session = request.getSession();
		PayPal paypal = new PayPal();
		// Paypal调用的returnServlet
		//String returnURL = "http://service.1yingli.cn/yiyingliService/Return?page=return";
		String returnURL = "http://service.1yingli.cn/yiyingliService/Return?page=return";
		// 当取消交易的时候，返回地址
		String cancelURL = testPage;
		Map<String, String> checkoutDetails = new HashMap<String, String>();
		checkoutDetails = setRequestParams(request);
		// 检查前台传来的数据
		if (request.getParameter("oid") == null || request.getParameter("uid") == null) {
			returnMsg(response, MsgUtil.getErrorMsg("data is incomplete"));
			return;
		}
		UserMarkService userMarkService = (UserMarkService) getApplicationContext().getBean("userMarkService");
		OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");
		LogUtil.info("receive>>>>orderId:" + request.getParameter("oid") + "\t uid:" + request.getParameter("uid"),
				this.getClass());
		// 商户网站订单系统中唯一订单号，必填
		Order order = orderService.queryByShowId(request.getParameter("oid"), false);
		if (order == null) {
			returnMsg(response, MsgUtil.getErrorMsg("order is not existed"));
			return;
		}
		// 用户id
		String uid = request.getParameter("uid");
		User user = userMarkService.queryUser(uid);
		if (user == null) {
			returnMsg(response, MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>createOrderId:" + order.getCreateUser().getId() + ",userId:" + user.getId() + ","
					+ (order.getCreateUser().getId() == user.getId()), this.getClass());
			returnMsg(response, MsgUtil.getErrorMsg("this order is not belong to you"));
			return;
		}
		String state = order.getState();
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			returnMsg(response, MsgUtil.getErrorMsg("order state is not accurate"));
			return;
		}
		// 由后台插入相关数据
		checkoutDetails.put("L_PAYMENTREQUEST_0_NAME0",  URLEncoder.encode(order.getServiceTitle(), "UTF-8"));
		// 货物id，这里填写的是导师id
		checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", order.getTeacher().getId().toString());
		checkoutDetails.put("L_PAYMENTREQUEST_0_DESC0", "Onemile:" + URLEncoder.encode(order.getServiceTitle(), "UTF-8"));
		checkoutDetails.put("L_PAYMENTREQUEST_0_QTY0", "1");
		// 商品价格
		float price = order.getMoney();
		price /= 6;
		BigDecimal b = new BigDecimal(price);
		price = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		if(price<0.01)
			price=(float) 0.01;
		checkoutDetails.remove("oid");
		checkoutDetails.remove("uid");
		checkoutDetails.put("PAYMENTREQUEST_0_ITEMAMT", price+"");
		checkoutDetails.put("PAYMENTREQUEST_0_HANDLINGAMT", "0");
		// 包括税款，手续费（这些我们都是零）的总金额
		checkoutDetails.put("PAYMENTREQUEST_0_AMT", price+"");
		// 我们的订单号
		checkoutDetails.put("PAYMENTREQUEST_0_CUSTOM", request.getParameter("oid"));
		checkoutDetails.put("REQCONFIRMSHIPPING", "0");
		checkoutDetails.put("NOSHIPPING", "1");
		
		checkoutDetails.put("PAYMENTREQUEST_0_CURRENCYCODE", "USD");
		checkoutDetails.put("PAYMENTREQUEST_0_PAYMENTACTION", "Sale");

		session.invalidate();
		session = request.getSession();
		Map<String, String> nvp = paypal.callShortcutExpressCheckout(checkoutDetails, returnURL, cancelURL);
		if(nvp==null){
			returnMsg(response, MsgUtil.getErrorMsg("fail to connect to PayPal, try again later"));
			return;
		}
		session.setAttribute("checkoutDetails", checkoutDetails);
		System.out.println(nvp);
		String strAck = nvp.get("ACK").toString().toUpperCase();
		if (strAck != null && (strAck.equals("SUCCESS") || strAck.equals("SUCCESSWITHWARNING"))) {
			session.setAttribute("TOKEN", nvp.get("TOKEN").toString());
			// Redirect to paypal.com
			paypal.redirectURL(response, nvp.get("TOKEN").toString(),
					(isSet(session.getAttribute("EXPRESS_MARK"))
							&& session.getAttribute("EXPRESS_MARK").equals("ECMark")
							|| (paypal.getUserActionFlag().equalsIgnoreCase("true"))));
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
			session.invalidate();
			returnToOnemile(resultParameter+"fail", response);

		}
	}

	private Map<String, String> setRequestParams(HttpServletRequest request) {
		Map<String, String> requestMap = new HashMap<String, String>();
		for (String key : request.getParameterMap().keySet()) {
			requestMap.put(key, StringEscapeUtils.escapeHtml4(request.getParameterMap().get(key)[0]));
		}

		return requestMap;

	}

	private boolean isSet(Object value) {
		return (value != null && value.toString().length() != 0);
	}

	public void returnToOnemile(String para, HttpServletResponse response) {
		try {
			response.sendRedirect(testPage+para);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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