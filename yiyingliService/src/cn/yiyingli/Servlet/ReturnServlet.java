package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.ExRewardForPay;
import cn.yiyingli.PayPal.PayPal;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.WarnUtil;

public class ReturnServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String page = "http://www.1yingli.cn/myTutor";

	private static final String resultParameter = "?paymentResult=";

	// 当payapl链接失败返回的网页
	private static final String connectError = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">    <title>你的导师</title>    <link rel=\"Shortcut Icon\" href=\"http://image.1yingli.cn/img/logo0.png\">    <link rel=\"Bookmark\" href=\"http://image.1yingli.cn/img/logo0.png\">    <style type=\"text/css\">    	#succ{    		width: 400px;height: 200px;margin: auto;position: fixed;top: 50%;left: 50%;margin-top:-150px;margin-left:-200px;border-radius: 10px;z-index: 101;background: #fff; border:1px solid #D2D2D2    	}    	.succ_title{    	  width: 400px;height: 35px;background-color: #d2d2d2;border-top-left-radius: 10px;border-top-right-radius: 10px;text-align: center;    	}    	.succ_title div{    	  font-size: 16px;padding-top: 5px;font-weight: bold;color:#FFF;    	}    	.succ_content{    		position: absolute;top: 40%;left: 33%;font-size: 20px;color: #b6b6b6;    	}    	#succ a{    		text-decoration: none;width: 128px;position: absolute;top: 70%;left: 35%;font-size: 20px;color: #FFF;background-color: #56bbe8;text-align: center;border-radius: 14px;    	}    </style></head><body>	<div id=\"succ\">    	<div class=\"succ_title\"><div>来自一英里的信息</div></div>    	<div class=\"succ_content\">连接PAYPAL失败或PAYPAL订单创建失败，请重试。</div>    	<a href=\"http://www.1yingli.cn/myTutor\">确定</a>		</div></body>";

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

	public ReturnServlet() {
		super();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String payerID = request.getParameter("PayerID");
		String token = request.getParameter("token");
		if (payerID == null || token == null) {
			LogUtil.error("Paypal return null payerID or null token", getClass());
			returnMsg(response, connectError);
			return;
		}
		// Check to see if the Request object contains a variable named 'token'
		PayPal pp = new PayPal();

		/*
		 * Calls the GetExpressCheckoutDetails API call
		 */
		Map<String, String> results = pp.getShippingDetails(token);
		// 检测paypal是否链接中断，没有返回内容
		if (results == null) {
			returnMsg(response, connectError);
			return;
		}
		// 根据之前传的callback参数修改callback
		if (results.get("PAYMENTREQUEST_0_CUSTOM") == null) {
			returnMsg(response, connectError);
			return;
		}

		String strAck = results.get("ACK").toString();

		String extra_common_param = results.get("PAYMENTREQUEST_0_CUSTOM");
		String[] param = extra_common_param.split("\\|");

		String olid = param[0];

		if (!(("SUCCESS".equalsIgnoreCase(strAck) || "SUCCESSWITHWARNING".equalsIgnoreCase(strAck)))) {
			// log the error information returned by PayPal
			String errorCode = results.get("L_ERRORCODE0").toString();
			String errorShortMsg = results.get("L_SHORTMESSAGE0").toString();
			String errorLongMsg = results.get("L_LONGMESSAGE0").toString();
			String errorSeverityCode = results.get("L_SEVERITYCODE0").toString();
			String errorString = "SetExpressCheckout API call failed. " + "Detailed Error Message: " + errorLongMsg
					+ "Short Error Message: " + errorShortMsg + "Error Code: " + errorCode + "Error Severity Code: "
					+ errorSeverityCode;
			LogUtil.error("After GetExpressCheckoutDetails from Paypal and ERROR INFO:" + errorString, this.getClass());
			returnToOnemile(resultParameter + "fail", response);
			return;
		}

		try {
			// 组装checkoutDetails
			Map<String, String> checkoutDetails = new HashMap<String, String>();

			checkoutDetails.put("L_PAYMENTREQUEST_0_NAME0", results.get("L_PAYMENTREQUEST_0_NAME0"));
			checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", results.get("L_PAYMENTREQUEST_0_NUMBER0"));
			checkoutDetails.put("L_PAYMENTREQUEST_0_DESC0", results.get("L_PAYMENTREQUEST_0_DESC0"));
			checkoutDetails.put("L_PAYMENTREQUEST_0_QTY0", results.get("L_PAYMENTREQUEST_0_QTY0"));
			checkoutDetails.put("PAYMENTREQUEST_0_ITEMAMT", results.get("PAYMENTREQUEST_0_ITEMAMT"));
			checkoutDetails.put("PAYMENTREQUEST_0_HANDLINGAMT", results.get("PAYMENTREQUEST_0_HANDLINGAMT"));
			checkoutDetails.put("PAYMENTREQUEST_0_AMT", results.get("PAYMENTREQUEST_0_AMT"));
			checkoutDetails.put("PAYMENTREQUEST_0_CUSTOM", results.get("PAYMENTREQUEST_0_CUSTOM"));

			checkoutDetails.put("REQCONFIRMSHIPPING", results.get("REQCONFIRMSHIPPING"));
			checkoutDetails.put("NOSHIPPING", results.get("NOSHIPPING"));

			checkoutDetails.put("PAYMENTREQUEST_0_CURRENCYCODE", results.get("PAYMENTREQUEST_0_CURRENCYCODE"));
			checkoutDetails.put("PAYMENTREQUEST_0_PAYMENTACTION", results.get("PAYMENTREQUEST_0_PAYMENTACTION"));

			checkoutDetails.put("paymentType", "Sale");
			checkoutDetails.put("currencyCodeType", "USD");
			checkoutDetails.put("TOKEN", token);
			checkoutDetails.put("payer_id", payerID);

			/*
			 * Calls the DoExpressCheckoutPayment API call
			 */

			if ("return".equals(request.getParameter("page"))) {
				HashMap<String, String> results2 = pp.confirmPayment(checkoutDetails, request.getServerName());
				// 检测paypal是否链接中断，没有返回内容
				if (results2 == null) {
					returnMsg(response, connectError);
					return;
				}
				// request.setAttribute("payment_method", "");
				String strAck2 = results2.get("ACK").toString().toUpperCase();
				if (("Success".equalsIgnoreCase(strAck2) || "SuccessWithWarning".equalsIgnoreCase(strAck))) {
					if (param.length > 2) {
						RewardService rewardService = (RewardService) getApplicationContext().getBean("rewardService");
						ExRewardForPay.dealReward(rewardService, extra_common_param, olid);
						request.setAttribute("ack", strAck);
						return;
					}
					// 检查数据库，并修改，最终完成订单
					OrderListService orderListService = (OrderListService) getApplicationContext()
							.getBean("orderListService");
					NotificationService notificationService = (NotificationService) getApplicationContext()
							.getBean("notificationService");
					if (results2.get("PAYMENTINFO_0_PAYMENTSTATUS").equals("Completed")) {
						OrderList orderList = orderListService.queryByOrderListNo(olid);
						// 检查订单是否存在
						if (orderList == null) {
							LogUtil.error("Return from Paypal and orderList is not exist. orderList id:" + olid,
									this.getClass());
							returnToOnemile(resultParameter + "fail", response);
							return;
						}
						// 检查订单款项是否正确
						float price = orderList.getPayMoney();
						price /= 6;
						BigDecimal b = new BigDecimal(price);
						price = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
						if (price < 0.01)
							price = 0.01F;
						if (!(Float.parseFloat(results2.get("PAYMENTINFO_0_AMT")) == price
								&& (results2.get("PAYMENTINFO_0_CURRENCYCODE").equals("USD")))) {
							LogUtil.error("Return from Paypal and orderList id:" + olid
									+ ", price is wrong, it should be " + orderList.getPayMoney() + ", but it is "
									+ Float.parseFloat(results2.get("PAYMENTREQUEST_0_AMT")), this.getClass());
							orderList.setState(OrderListService.ORDER_STATE_ABNORMAL + "," + orderList.getState());
							WarnUtil.sendWarnToCTO("Return from Paypal and orderList id:" + olid
									+ ", price is wrong, it should be " + orderList.getPayMoney() + ", but it is "
									+ Float.parseFloat(results2.get("PAYMENTREQUEST_0_AMT")));

							orderListService.update(orderList);

							returnToOnemile(resultParameter + "fail", response);
							return;
						}
						// 检查订单状态是否正确
						String state = orderList.getState().split(",")[0];
						// 该情况应该不会出现
						if (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID)) {
							LogUtil.error(
									"Return from Paypal and orderList id:" + olid
											+ ", orderList has paid and maybe this is duplicate notify from Paypal",
									this.getClass());
							returnToOnemile(resultParameter + "fail", response);
							return;
						}
						if (!state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
							LogUtil.error("Return from Paypal and orderList id:" + olid + ", state is wrong",
									this.getClass());
							WarnUtil.sendWarnToCTO("Return from Paypal and orderList id:" + olid + ", state is wrong");
							returnToOnemile(resultParameter + "fail", response);
							return;
						}
						// 订单貌似没有异常，因此根据Paypal信息处理订单
						// order.setState(
						// cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID
						// + "," + order.getState());
						// order.setPayMethod(OrderService.ORDER_PAYMETHOD_PAYPAL);
						// orderService.updateAndPlusNumber(order);
						// NotifyUtil.notifyUserOrder(order.getCustomerPhone(),
						// order.getCustomerEmail(),
						// "尊敬的用户，订单号为" + order.getOrderNo() +
						// "的订单已经付款完成，请等待导师接受订单", order.getCreateUser(),
						// notificationService);
						// NotifyUtil.notifyTeacher(order, "尊敬的导师，订单号为" +
						// order.getOrderNo() + "的订单，用户("
						// + order.getCustomerName() + ")已经付款，等待您的接受。",
						// notificationService);
						finishOrder(orderListService, orderList, notificationService);
					} else {
						LogUtil.error("Return from Paypal and payment status is:"
								+ results2.get("PAYMENTINFO_0_PAYMENTSTATUS"), this.getClass());
					}
					request.setAttribute("ack", strAck);
				} else {
					// log error information returned by PayPal
					String errorCode = results2.get("L_ERRORCODE0").toString();
					String errorShortMsg = results2.get("L_SHORTMESSAGE0").toString();
					String errorLongMsg = results2.get("L_LONGMESSAGE0").toString();
					String errorSeverityCode = results2.get("L_SEVERITYCODE0").toString();
					String errorString = "SetExpressCheckout API call failed. " + "Detailed Error Message: "
							+ errorLongMsg + "Short Error Message: " + errorShortMsg + "Error Code: " + errorCode
							+ "Error Severity Code: " + errorSeverityCode;
					LogUtil.error("After DoExpressCheckoutPayment from Paypal and ERROR INFO:" + errorString,
							this.getClass());
					returnToOnemile(resultParameter + "fail", response);
					return;
				}
			} else {
				returnMsg(response, "无权限调用此接口");
				return;
			}
			returnToOnemile(resultParameter + "success", response);
		} catch (Exception e) {
			e.printStackTrace();
			returnToOnemile(resultParameter + "fail", response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void returnToOnemile(String para, HttpServletResponse response) {
		try {
			response.sendRedirect(page + para);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void finishOrder(OrderListService orderListService, OrderList orderList,
			NotificationService notificationService) {
		String time = Calendar.getInstance().getTimeInMillis() + "";
		for (Order order : orderList.getOrders()) {
			order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
			order.setPayTime(time);
			order.setPayMethod(OrderService.ORDER_PAYMETHOD_PAYPAL);
			NotifyUtil.notifyManager(new SuperMap().put("type", "waitConfirm").finishByJson());
//			TimeTaskUtil.sendTimeTask("change", "order",
//					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) + "",
//					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		}
		orderList.setState(OrderListService.ORDER_STATE_FINISH_PAID + "," + orderList.getState());
		orderListService.updateAndPlusNumber(orderList);
		NotifyUtil.notifyUserOrder(orderList, "尊敬的用户，流水号为" + orderList.getOrderListNo() + "的订单组已经付款完成，请等待导师接受订单",
				orderList.getUser(), notificationService);
		NotifyUtil.notifyTeacher(orderList,
				"尊敬的导师，流水号为" + orderList.getOrderListNo() + "的订单组，用户(" + orderList.getCustomerName() + ")已经付款，等待您的接受。",
				notificationService);
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
