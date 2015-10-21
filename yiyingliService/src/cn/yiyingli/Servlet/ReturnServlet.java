package cn.yiyingli.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.PayPal.PayPal;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.WarnUtil;

public class ReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String page = "http://www.1yingli.cn/yourTutor.html";

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

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);

		if (isSet(request.getParameter("PayerID")))
			session.setAttribute("payer_id", request.getParameter("PayerID"));
		String token = "";
		if (isSet(request.getParameter("token"))) {
			session.setAttribute("TOKEN", request.getParameter("token"));
			token = request.getParameter("token");
		} else {
			token = (String) session.getAttribute("TOKEN");
		}

		// Check to see if the Request object contains a variable named 'token'
		PayPal pp = new PayPal();
		Map<String, String> result = new HashMap<String, String>();
		// If the Request object contains the variable 'token' then it means
		// that the user is coming from PayPal site.

		if (isSet(token)) {
			/*
			 * Calls the GetExpressCheckoutDetails API call
			 */
			Map<String, String> results = pp.getShippingDetails(token);
			String strAck = results.get("ACK").toString();
			if (strAck != null
					&& (strAck.equalsIgnoreCase("SUCCESS") || strAck.equalsIgnoreCase("SUCCESSWITHWARNING"))) {
				session.setAttribute("payer_id", results.get("PAYERID"));
				result.putAll(results);
				/*
				 * The information that is returned by the
				 * GetExpressCheckoutDetails call should be integrated by the
				 * partner into his Order Review page
				 * 
				 */
				/*
				String email = results.get("EMAIL");
				String payerId = results.get("PAYERID"); 
				String payerStatus = results.get("PAYERSTATUS"); 
				String firstName = results.get("FIRSTNAME"); 
				String lastName = results.get("LASTNAME"); 
				String shipToName = results.get("PAYMENTREQUEST_0_SHIPTONAME"); 
				String shipToStreet = results.get("PAYMENTREQUEST_0_SHIPTOSTREET");
				String shipToCity = results.get("PAYMENTREQUEST_0_SHIPTOCITY"); 
				String shipToState = results.get("PAYMENTREQUEST_0_SHIPTOSTATE"); 
				String shipToCntryCode = results.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE"); 
				String shipToZip = results.get("PAYMENTREQUEST_0_SHIPTOZIP"); 
				String addressStatus = results.get("ADDRESSSTATUS"); 
				String totalAmt = results.get("PAYMENTREQUEST_0_AMT"); 
				String currencyCode = results.get("CURRENCYCODE"); 
				*/

			} else {
				// log the error information returned by PayPal
				String errorCode = results.get("L_ERRORCODE0").toString();
				String errorShortMsg = results.get("L_SHORTMESSAGE0").toString();
				String errorLongMsg = results.get("L_LONGMESSAGE0").toString();
				String errorSeverityCode = results.get("L_SEVERITYCODE0").toString();
				String errorString = "SetExpressCheckout API call failed. " + "Detailed Error Message: "
						+ errorLongMsg + "Short Error Message: " + errorShortMsg + "Error Code: " + errorCode
						+ "Error Severity Code: " + errorSeverityCode;
				LogUtil.error("After GetExpressCheckoutDetails from Paypal and ERROR INFO:" + errorString ,
						this.getClass());
				session.invalidate();
				returnToOnemile(request, response);
				return;
			}
		}
		try {
			Map<String, String> checkoutDetails = new HashMap<String, String>();
			checkoutDetails.putAll((Map<String, String>) session.getAttribute("checkoutDetails"));
			checkoutDetails.putAll(setRequestParams(request));
			checkoutDetails.put("TOKEN", token);
			checkoutDetails.put("payer_id", (String) session.getAttribute("payer_id"));
			/*
			 * Calls the DoExpressCheckoutPayment API call
			 */
			
			if (isSet(request.getParameter("page")) && request.getParameter("page").equals("return")) {
				// FIXME - The method 'request.getServerName()' must be
				// sanitized before being used.
				HashMap results = pp.confirmPayment(checkoutDetails, request.getServerName());
				request.setAttribute("payment_method", "");
				String strAck = results.get("ACK").toString().toUpperCase();
				if (strAck != null
						&& (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
					//result存着所有信息
					result.putAll(results);
					result.putAll(checkoutDetails);
					// 检查数据库，并修改，最终完成订单
					OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");
					NotificationService notificationService = (NotificationService) getApplicationContext()
							.getBean("notificationService");
					if (result.get("PAYMENTINFO_0_PAYMENTSTATUS").equals("Completed")) {
						String oid = result.get("PAYMENTREQUEST_0_CUSTOM");
						Order order = orderService.queryByShowId(oid, false);
						// 检查订单是否存在
						if (order == null) {
							LogUtil.error("Return from Paypal and order is not exist. order id:" + oid,
									this.getClass());
							session.invalidate();
							returnToOnemile(request, response);
							return;
						}
						// 检查订单款项是否正确
						if (!(Float.parseFloat(result.get("PAYMENTREQUEST_0_AMT")) == order.getMoney())) {
							LogUtil.error("Return from Paypal and order id:" + oid + ", price is wrong, it should be "
									+ order.getMoney() + ", but it is " + Float.parseFloat(result.get("PAYMENTREQUEST_0_AMT")),
									this.getClass());
							order.setState(
									cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL + "," + order.getState());
							WarnUtil.sendWarnToCTO("Return from Paypal and order id:" + oid
									+ ", price is wrong, it should be " + order.getMoney() + ", but it is "
									+ Float.parseFloat(result.get("PAYMENTREQUEST_0_AMT")));
							orderService.update(order);
							session.invalidate();
							returnToOnemile(request, response);
							return;
						}
						// 检查订单状态是否正确
						String state = order.getState().split(",")[0];
						// 该情况应该不会出现
						if (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID)) {
							LogUtil.error(
									"Return from Paypal and order id:" + oid
											+ ", order has paid and maybe this is duplicate notify from Paypal",
									this.getClass());
							session.invalidate();
							returnToOnemile(request, response);
							return;
						}
						if (!state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
							LogUtil.error("Return from Paypal and order id:" + oid + ", state is wrong",
									this.getClass());
							WarnUtil.sendWarnToCTO("Return from Paypal and order id:" + oid + ", state is wrong");
							session.invalidate();
							returnToOnemile(request, response);
							return;
						}
						// 订单貌似没有异常，因此根据Paypal信息处理订单
						order.setState(
								cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
						orderService.updateAndPlusNumber(order);
						NotifyUtil.notifyUser(order.getCustomerPhone(), order.getCustomerEmail(),
								"尊敬的用户，订单号为" + order.getOrderNo() + "的订单已经付款完成，请等待导师接受订单", order.getCreateUser(),
								notificationService);
						NotifyUtil.notifyTeacher(order.getTeacher().getPhone(),
								order.getTeacher().getEmail(), "尊敬的导师，订单号为" + order.getOrderNo() + "的订单，用户("
										+ order.getCustomerName() + ")已经付款，等待您的接受。",
								order.getTeacher(), notificationService);
					} else {
						// TODO:other case
						LogUtil.error(
								"Return from Paypal and payment status is:" + result.get("PAYMENTINFO_0_PAYMENTSTATUS")
										,
								this.getClass());
					}
					request.setAttribute("ack", strAck);
					session.invalidate();
				} else {
					// log error information returned by PayPal
					String errorCode = results.get("L_ERRORCODE0").toString();
					String errorShortMsg = results.get("L_SHORTMESSAGE0").toString();
					String errorLongMsg = results.get("L_LONGMESSAGE0").toString();
					String errorSeverityCode = results.get("L_SEVERITYCODE0").toString();
					String errorString = "SetExpressCheckout API call failed. " + "Detailed Error Message: "
							+ errorLongMsg + "Short Error Message: " + errorShortMsg + "Error Code: " + errorCode
							+ "Error Severity Code: " + errorSeverityCode;
					LogUtil.error("After GetExpressCheckoutDetails from Paypal and ERROR INFO:" + errorString ,
							this.getClass());
					session.invalidate();
					returnToOnemile(request, response);
					return;
				}
			}
			request.setAttribute("result", result);
			returnToOnemile(request, response);
		} catch (Exception e) {
			String errorString = e.getCause().getMessage();
			request.setAttribute("error", errorString);
			returnToOnemile(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Map<String, String> setRequestParams(HttpServletRequest request) {
		Map<String, String> requestMap = new HashMap<String, String>();
		for (String key : request.getParameterMap().keySet()) {

			requestMap.put(key, request.getParameterMap().get(key)[0]);
		}

		return requestMap;

	}

	private boolean isSet(Object value) {
		return (value != null && value.toString().length() != 0);
	}
	
	public void returnToOnemile(HttpServletRequest request, HttpServletResponse response){
		/*
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		if (dispatcher != null) {
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		try {
			response.sendRedirect(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
