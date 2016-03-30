package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.ExchangeData.ExRewardForPay;
import cn.yiyingli.PayPal.PayPal;
import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;

public class RewardPayPalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2722761580200224133L;

	// private static final String testPage =
	// "http://testweb.1yingli.cn/yourTutor.html";

	// 当payapl链接失败返回的网页
	private static final String connectError = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">    <title>你的导师</title>    <link rel=\"Shortcut Icon\" href=\"http://image.1yingli.cn/img/logo0.png\">    <link rel=\"Bookmark\" href=\"http://image.1yingli.cn/img/logo0.png\">    <style type=\"text/css\">    	#succ{    		width: 400px;height: 200px;margin: auto;position: fixed;top: 50%;left: 50%;margin-top:-150px;margin-left:-200px;border-radius: 10px;z-index: 101;background: #fff; border:1px solid #D2D2D2    	}    	.succ_title{    	  width: 400px;height: 35px;background-color: #d2d2d2;border-top-left-radius: 10px;border-top-right-radius: 10px;text-align: center;    	}    	.succ_title div{    	  font-size: 16px;padding-top: 5px;font-weight: bold;color:#FFF;    	}    	.succ_content{    		position: absolute;top: 40%;left: 33%;font-size: 20px;color: #b6b6b6;    	}    	#succ a{    		text-decoration: none;width: 128px;position: absolute;top: 70%;left: 35%;font-size: 20px;color: #FFF;background-color: #56bbe8;text-align: center;border-radius: 14px;    	}    </style></head><body>	<div id=\"succ\">    	<div class=\"succ_title\"><div>来自一英里的信息</div></div>    	<div class=\"succ_content\">支付连接超时，请重新支付。</div>    	<a href=\"";
	private static final String connectError2 = "\">确定</a>		</div></body>";

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

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PayPal paypal = new PayPal();
		// Paypal调用的returnServlet
		String returnURL = "http://service.1yingli.cn/yiyingliService/Return?page=return";

		Map<String, String> checkoutDetails = new HashMap<String, String>();
		// 检查前台传来的数据
		if (req.getParameter("teacherId") == null || req.getParameter("money") == null
				|| req.getParameter("teacherName") == null || req.getParameter("callback") == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("00001"));
			return;
		}
		LogUtil.info("receive>>PAYPAL>>reward--->teacherName:" + req.getParameter("teacherName") + "\t teacherId:"
				+ req.getParameter("teacherId") + "\t money:" + req.getParameter("money"), this.getClass());
		UserMarkService userMarkService = (UserMarkService) getApplicationContext().getBean("userMarkService");
		RewardService rewardService = (RewardService) getApplicationContext().getBean("rewardService");

		String teacherId = req.getParameter("teacherId");
		String teacherName = req.getParameter("teacherName");
		String money = req.getParameter("money");
		String uid = req.getParameter("uid");
		String passageId = req.getParameter("passageId");
		String callback = req.getParameter("callback");

		Long userId = null;
		String userName = null;
		if (uid != null) {
			User user = userMarkService.queryUser(uid);
			if (user != null) {
				userId = user.getId();
				userName = user.getName();
			}
		}

		// 订单名称
		String subject = "打赏-" + teacherName;
		// 必填

		// 订单描述
		String body = "打赏-" + teacherName;

		Reward reward = new Reward();
		String time = Calendar.getInstance().getTimeInMillis() + "";
		reward.setCreateTime(time);
		reward.setFinishPay(false);
		reward.setFinishSalary(false);
		reward.setMoney(Float.valueOf(money));
		reward.setTeacherId(Long.valueOf(teacherId));
		reward.setTeacherName(teacherName);
		reward.setUserId(userId);
		reward.setUserName(userName);

		reward.setPassageId(passageId == null ? null : Long.valueOf(passageId));

		rewardService.save(reward);

		String oid = ExRewardForPay.getRewardNo(reward.getId());
		// 由后台插入相关数据
		checkoutDetails.put("L_PAYMENTREQUEST_0_NAME0", URLEncoder.encode(subject, "UTF-8"));
		// 货物id，这里填写的是订单号
		checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", oid);
		checkoutDetails.put("L_PAYMENTREQUEST_0_DESC0", URLEncoder.encode("【一英里】" + body, "UTF-8"));
		checkoutDetails.put("L_PAYMENTREQUEST_0_QTY0", "1");
		// 商品价格
		float price = Float.valueOf(money);

		price /= 6;
		BigDecimal b = new BigDecimal(price);
		price = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		if (price < 0.01)
			price = 0.01F;

		checkoutDetails.put("PAYMENTREQUEST_0_ITEMAMT", price + "");
		checkoutDetails.put("PAYMENTREQUEST_0_HANDLINGAMT", "0");
		// 包括税款，手续费（这些我们都是零）的总金额
		checkoutDetails.put("PAYMENTREQUEST_0_AMT", price + "");
		// 我们的订单号,以及微信端回调页面（可选）

		checkoutDetails.put("PAYMENTREQUEST_0_CUSTOM", oid + "|" + ExRewardForPay.getExtraParams(reward.getId()));
		checkoutDetails.put("REQCONFIRMSHIPPING", "0");
		checkoutDetails.put("NOSHIPPING", "1");

		checkoutDetails.put("PAYMENTREQUEST_0_CURRENCYCODE", "USD");
		checkoutDetails.put("PAYMENTREQUEST_0_PAYMENTACTION", "Sale");

		Map<String, String> nvp = paypal.callShortcutExpressCheckout(checkoutDetails, returnURL, callback);
		if (nvp == null) {
			returnMsg(resp, connectError + callback + connectError2);
			return;
		}
		String strAck = nvp.get("ACK").toString().toUpperCase();
		if (strAck != null && (strAck.equals("SUCCESS") || strAck.equals("SUCCESSWITHWARNING"))) {
			// Redirect to paypal.com
			paypal.redirectURL(resp, nvp.get("TOKEN").toString(), paypal.getUserActionFlag().equalsIgnoreCase("true"));
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
			returnToOnemile(callback, resp);

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

	public void returnToOnemile(String url, HttpServletResponse response) {
		try {
			response.sendRedirect(url + "?state=fail");
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
