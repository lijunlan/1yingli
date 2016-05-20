package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.yiyingli.BaichuanTaobaoUtil.CloudPushUtil;
import cn.yiyingli.Dao.NotificationDao;
import cn.yiyingli.Persistant.*;
import cn.yiyingli.Handle.Service.MOrderSalaryDoneService;
import cn.yiyingli.Handle.Service.TRefuseOrderService;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;

public class NotifyUtil {


	private final static String TOTEACHER = "(<a href=\"http://www.1yingli.cn/myStudent\">查看订单</a>)";
	private final static String TOUSER = "(<a href=\"http://www.1yingli.cn/myTutor\">查看订单</a>)";

	/**
	 * @param phone
	 * @param email
	 * @param message
	 * @param user    用户标识 UUID
	 */
	public static boolean notifyUserOrder(String phone, String email, String message, User user,
										  NotificationService notificationService) {
		return notifyUserNormal(phone, email, "订单状态改变通知", message, user, notificationService);
	}

//	public static boolean notifyUserOrder(OrderList orderList, String message, User user,
//										  NotificationService notificationService) {
//		return notifyUserOrder(orderList.getOrders().get(0).getCustomerPhone(),
//				orderList.getOrders().get(0).getCustomerEmail(), message, user,
//				notificationService);
//	}


	public static boolean notifyPayUser(OrderList orderList, NotificationService notificationService) {
		Map<String, String> phoneMap = new HashMap<>();
		Map<String, String> emailMap = new HashMap<>();
		for (Order order : orderList.getOrders()) {
			String customerPhone = order.getCustomerPhone();
			String customerEmail = order.getCustomerEmail();
			String orderNo = order.getOrderNo();
			if (phoneMap.containsKey(customerPhone)) {
				phoneMap.put(customerPhone, orderNo + "," + phoneMap.get(customerPhone));
			} else {
				phoneMap.put(customerPhone, orderNo);
			}
			if (emailMap.containsKey(customerEmail)) {
				emailMap.put(customerEmail, orderNo + "," + emailMap.get(customerEmail));
			} else {
				emailMap.put(customerEmail, orderNo);
			}
		}
		for (String phoneNum : phoneMap.keySet()) {
			String message = "尊敬的用户，订单号为" + phoneMap.get(phoneNum) + "的订单组已经付款完成，请等待接受订单"
					+ "(http://www.1yingli.cn/myTutor)";
			if (CheckUtil.checkMobileNumber(phoneNum) || CheckUtil.checkGlobleMobileNumber(phoneNum)) {
				SendMessageUtil.sendMessage(phoneNum, message);
			}
		}
		for (String email : emailMap.keySet()) {
			String message = "尊敬的用户，订单号为" + emailMap.get(email) + "的订单组已经付款完成，请等待接受订单"
					+ "(<a href=\"http://www.1yingli.cn/myTutor\">查看订单</a>)";
			if (CheckUtil.checkEmail(email)) {
				SendMailUtil.sendMessage(email, "订单状态改变通知", message);
			}
		}
		for (AdditionalPay additionalPay : orderList.getAdditionalPays()) {
			Order order = additionalPay.getOrder();
			String customerPhone = order.getCustomerPhone();
			String customerEmail = order.getCustomerEmail();
			String orderNo = order.getOrderNo();
			String message = "尊敬的用户，订单号为" + orderNo + "的订单已经追加付款"
					+ additionalPay.getMoney() + "(http://www.1yingli.cn/myTutor)";
			if (CheckUtil.checkMobileNumber(customerPhone) || CheckUtil.checkGlobleMobileNumber(customerPhone)) {
				SendMessageUtil.sendMessage(customerPhone, message);
			}
			message = "尊敬的用户，订单号为" + orderNo + "的订单已经追加付款"
					+ additionalPay.getMoney() + "(<a href=\"http://www.1yingli.cn/myTutor\">查看订单</a>)";
			if (CheckUtil.checkEmail(customerEmail)) {
				SendMailUtil.sendMessage(customerEmail, "订单状态改变通知", message);
			}
		}
		return true;
	}

	public static boolean notifyPayTeacher(OrderList orderList, NotificationService notificationService) {
		Map<String, String> phoneMap = new HashMap<>();
		Map<String, String> emailMap = new HashMap<>();
		for (Order order : orderList.getOrders()) {
			String teacherPhone = order.getTeacher().getPhone();
			String teacherEmail = order.getTeacher().getEmail();
			String orderNo = order.getOrderNo();
			if (phoneMap.containsKey(teacherPhone)) {
				phoneMap.put(teacherPhone, orderNo + "," + phoneMap.get(teacherPhone));
			} else {
				phoneMap.put(teacherPhone, orderNo);
			}
			if (emailMap.containsKey(teacherEmail)) {
				emailMap.put(teacherEmail, orderNo + "," + emailMap.get(teacherEmail));
			} else {
				emailMap.put(teacherEmail, orderNo);
			}
		}
		for (String phoneNum : phoneMap.keySet()) {
			String message = "尊敬的用户，订单号为" + phoneMap.get(phoneNum) + "的订单组已经付款完成，等待您的接受"
					+ "(http://www.1yingli.cn/myStudent)";
			if (CheckUtil.checkMobileNumber(phoneNum) || CheckUtil.checkGlobleMobileNumber(phoneNum)) {
				SendMessageUtil.sendMessage(phoneNum, message);
			}
		}
		for (String email : emailMap.keySet()) {
			String message = "尊敬的用户，订单号为" + emailMap.get(email) + "的订单组已经付款完成，等待您的接受"
					+ "(<a href=\"http://www.1yingli.cn/myStudent\">查看订单</a>)";
			if (CheckUtil.checkEmail(email)) {
				SendMailUtil.sendMessage(email, "订单状态改变通知", message);
			}
		}
		for (AdditionalPay additionalPay : orderList.getAdditionalPays()) {
			Order order = additionalPay.getOrder();
			String teacherPhone = order.getTeacher().getPhone();
			String teacherEmail = order.getTeacher().getEmail();
			String orderNo = order.getOrderNo();
			String message = "尊敬的用户，订单号为" + orderNo + "的订单已经追加付款"
					+ additionalPay.getMoney() + "(http://www.1yingli.cn/myTutor)";
			if (CheckUtil.checkMobileNumber(teacherPhone) || CheckUtil.checkGlobleMobileNumber(teacherPhone)) {
				SendMessageUtil.sendMessage(teacherPhone, message);
			}
			message = "尊敬的用户，订单号为" + orderNo + "的订单已经追加付款"
					+ additionalPay.getMoney() + "(<a href=\"http://www.1yingli.cn/myTutor\">查看订单</a>)";
			if (CheckUtil.checkEmail(teacherEmail)) {
				SendMailUtil.sendMessage(teacherEmail, "订单状态改变通知", message);
			}
		}
		return true;
	}

	public static boolean notifyUserOrder(Order order, String message, User user,
										  NotificationService notificationService) {
		return notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(), message, user, notificationService);
	}

	private static String getTeacherOrderMessageByState(Order order, String operation) {
		String states[] = order.getState().split(",");
		String No = order.getOrderNo();
		Teacher teacher = order.getTeacher();
		String nowState = states[0];
		if (operation.equals(MOrderSalaryDoneService.class.getName())) {
			return "尊敬的用户，订单(订单号:" + No + "),酬劳已成功转出，请注意查收.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
		}
		if (states.length > 1) {
			String lastState = states[1];
			if (nowState.equals(OrderService.ORDER_STATE_USER_REGRET)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + "),客户申请退款，等待您的确认，请在5天内到平台进行操作，超时系统会自动同意退款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_REGRET)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				return "尊敬的用户，订单(订单号:" + No + "),客户申请退款，等待您的确认。请在5天内到平台进行操作，超时系统会自动同意退款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + "),已经被用户取消。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_DISLIKE)
					&& lastState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)) {
				return "尊敬的用户，订单(订单号:" + No + "),对本次服务不满,申请退款,等待您的回复,请您在五天内到平台进行操作,超时系统将自动同意退款.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				// return "尊敬的用户，订单(订单号:" + No + ")已自动确认服务完毕,请等待用户确认本次服务.";
				return "";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_DISLIKE)) {
				return "尊敬的用户，订单(订单号:" + No + "),由于您长时间未作出响应,系统已自动确认同意退款,如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_REGRET)) {
				return "尊敬的用户，订单(订单号:" + No + "),由于您长时间未作出响应,系统已自动确认同意退款,如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_COMMENT)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。您的酬劳会在24小时内到账.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_END_FAILED)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。您的酬劳会在24小时内到账.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：同意此次退款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
//				return "尊敬的用户，订单(订单号:" + No + ")已被您接受,请及时与客户联系并确认服务时间,之后请到一英里平台登记服务时间。(客户姓名:" + order.getCustomerName()
//						+ ",电话:" + order.getCustomerPhone() + ",邮箱:" + order.getCustomerEmail() + ",微信:"
//						+ order.getCustomerContact() + ")";
				return "";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
//				return "尊敬的用户，订单(订单号:" + No + ")已被您接受,请及时为客户提供服务。(客户姓名:" + order.getCustomerName() + ",电话:"
//						+ order.getCustomerPhone() + ",邮箱:" + order.getCustomerEmail() + ",微信:"
//						+ order.getCustomerContact() + ")";
				return "";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
//				return "尊敬的用户，订单(订单号:" + No + ")已经进入等待服务状态,请在" + order.getOkTime() + "，按时进行服务.(客户姓名:"
//						+ order.getCustomerName() + ",电话:" + order.getCustomerPhone() + ",邮箱:"
//						+ order.getCustomerEmail() + ",微信:" + order.getCustomerContact() + ").系统会在约定时间("
//						+ order.getOkTime() + ")2周后自动确认服务完毕.";
				return "";
			} else if (nowState.equals(OrderService.ORDER_STATE_FINISH_PAID)
					&& lastState.equals(OrderService.ORDER_STATE_NOT_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")，客户(" + order.getCustomerName() + ")已经付款，等待您的接受。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_BARGAINED_NOT_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")，客户(" + order.getCustomerName() + ")已经按议价金额付款，等待您的服务。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else {
				return "";
			}
		} else {
			if (nowState.equals(OrderService.ORDER_NOT_BARGAINED)) {
				return "尊敬的用户，订单(订单号:" + No + ")，客户(" + order.getCustomerName() + ")已经下单，等待您商议具体价格。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else {
				return "";
			}
		}
	}

	private static String getUserOrderListMessageByState(String state, String No) {
		String states[] = state.split(",");
		String nowState = states[0];
		if (states.length > 1) {
			String lastState = states[1];
			if (nowState.equals(OrderListService.ORDER_STATE_CANCEL_PAID)
					&& lastState.equals(OrderListService.ORDER_STATE_NOT_PAID)) {
				return "尊敬的用户，订单(流水号:" + No + ")由于超时未支付,已被系统自动关闭,如有需要,请重新预约。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	private static String getUserOrderMessageByState(Order order, String operation) {
		String states[] = order.getState().split(",");
		String No = order.getOrderNo();
		Teacher teacher = order.getTeacher();
		String nowState = states[0];
		if (states.length > 1) {
			String lastState = states[1];
			if (nowState.equals(OrderService.ORDER_STATE_USER_REGRET)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + ")已经申请取消,请等待[" + teacher.getName() + "]同意.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				if (operation.equals(TRefuseOrderService.class.getName())) {
					return "尊敬的用户，订单(订单号:" + No + ")已被导师[" + teacher.getName() + "]拒绝,拒绝理由:" + order.getRefuseReason()
							+ ",您可预约其他优秀的导师哦,预付款将在24小时内退还到您的账户。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
				} else {
					return "尊敬的用户，订单(订单号:" + No + ")已经申请取消,请等待[" + teacher.getName() + "]同意.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
				}
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")已经取消，我们将竭尽全力在24小时内为您全额退款.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_DISLIKE)
					&& lastState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)) {
				return "尊敬的用户,订单(订单号:" + No + ")已经申请退款,请等待[" + teacher.getName() + "]确认.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				return "尊敬的用户,订单(订单号:" + No + "),[" + teacher.getName() + "]已经确认服务完毕.请到一英里平台确认本次服务完成.(系统会在1周后自动确认服务).如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_DISLIKE)) {
				return "尊敬的用户,订单(订单号:" + No + "),[" + teacher.getName() + "]已同意退款,我们将竭尽全力在24内退还到您的账户,请注意查收.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_REGRET)) {
				return "尊敬的用户,订单(订单号:" + No + "),[" + teacher.getName() + "]已同意退款,我们将竭尽全力在24内退还到您的账户,请注意查收.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_COMMENT)
					&& lastState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)) {
				return "尊敬的用户,订单(订单号:" + No + "),系统已经帮您自动确认本次服务,请到一英里平台对本次服务进行评价,留下您宝贵的意见.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_COMMENT)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。请到一英里平台对本次服务进行评价.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_END_FAILED)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：同意此次退款。我们将在24小时内给您退款.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_END_FAILED)
					&& lastState.equals(OrderService.ORDER_STATE_RETURN_SUCCESS)) {
				return "尊敬的用户，订单(订单号:" + No + "),已经成功退款,请注意查收.如有疑问请咨询客服.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经取消服务,本次预付款将在24小时内为您退款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经接受,TA将通过您提供的联系方式与您联系,请等待TA与您确认服务时间。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经接受,TA将通过您提供的联系方式与您联系,请等待服务。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_MANAGER_IN)
					&& (OrderService.ORDER_STATE_USER_DISLIKE.equals(lastState)
					|| OrderService.ORDER_STATE_USER_REGRET.equals(lastState))) {
				return "尊敬的用户，订单(订单号:" + No + ")的退款申请已被[" + teacher.getName() + "]拒绝,客服将介入此订单,请耐心等待,我们会在48小时内与您联系。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已确认服务时间为(" + order.getOkTime() + "),请等待服务.如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + ")已经被[" + teacher.getName() + "]取消,您可以在平台选择其他优秀的导师进行服务。本次预付款将在24小时内为您退款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_BARGAINING)
					&& lastState.equals(OrderService.ORDER_NOT_BARGAINED)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经接受,TA将通过您提供的联系方式与您联系进行议价,请等待TA与您确认服务金额。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else if (nowState.equals(OrderService.ORDER_BARGAINED_NOT_PAID)
					&& lastState.equals(OrderService.ORDER_BARGAINING)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经与您确定服务金额为:" + order.getMoney() + "，请及时完成付款。如有疑问请联系小助手,小助手微信号:yiyinglikeke";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public static boolean notifyUserOrder(OrderList orderList, NotificationService notificationService) {
		String message = getUserOrderListMessageByState(orderList.getState(), orderList.getOrderListNo());
		if (message.equals("")) {
			return false;
		}
		return true;
//				notifyUserNormal(orderList.getCustomerPhone(), orderList.getCustomerEmail(), "订单状态改变通知", message,
//				orderList.getUser(), notificationService);
	}

	public static boolean notifyUserOrder(Order order, NotificationService notificationService) {
		String message = getUserOrderMessageByState(order, "");
		if (message.equals("")) {
			return false;
		}
		return notifyUserNormal(order.getCustomerPhone(), order.getCustomerEmail(), "订单状态改变通知", message,
				order.getCreateUser(), notificationService);
	}

	public static boolean notifyUserOrder(Order order, Class<?> operationClass,
										  NotificationService notificationService) {
		String message = getUserOrderMessageByState(order, operationClass.getName());
		return notifyUserNormal(order.getCustomerPhone(), order.getCustomerEmail(), "订单状态改变通知", message,
				order.getCreateUser(), notificationService);
	}

	public static boolean notifyUserNormal(String phone, String email, String title, String message, User user) {
		if (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)) {
			SendMessageUtil.sendMessage(phone, message);
		}
		if (CheckUtil.checkEmail(email)) {
			SendMailUtil.sendMessage(email, title, message);
		}
		return true;
	}

	public static boolean notifyUserNormal(String phone, String email, String title, String message, User user,
										   NotificationService notificationService) {
		String m1 = message + "(<a href=\"http://www.1yingli.cn/myTutor\">查看订单</a>)";
		String m2 = message + "(http://www.1yingli.cn/myTutor)";
		if (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)) {
			SendMessageUtil.sendMessage(phone, m2);
		}
		if (CheckUtil.checkEmail(email)) {
			SendMailUtil.sendMessage(email, title, m1);
		}
		// web
		sendNotification(user, notificationService, message);
		// mobile
		String[] usernames = {user.getUsername()};
		CloudPushUtil.IOSpushMessageToAccount(usernames, message);
		CloudPushUtil.IOSpushNoticeToAccount(usernames, message);
		return true;
	}

	public static boolean notifyBD(String msg) {
		SendMailUtil.sendMessage("boom@1yingli.cn", "订单状态通知", msg);
		return true;
	}

	//	public static boolean notifyTeacher(OrderList orderList, String message, NotificationService notificationService) {
//		return notifyTeacher(orderList.getTeacher().getPhone(), orderList.getTeacher().getEmail(), message,
//				orderList.getTeacher().getId(), orderList.getTeacher().getUsername(), notificationService);
//	}
	public static boolean notifyTeacher(Order order, NotificationService notificationService) {
		String message = getTeacherOrderMessageByState(order, "");
		if (message.equals("")) {
			return false;
		}
		return notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(), message,
				order.getTeacher().getId(), order.getTeacher().getUsername(), notificationService);
	}

	public static boolean notifyTeacher(Order order, Class<?> operatedClass, NotificationService notificationService) {
		String message = getTeacherOrderMessageByState(order, operatedClass.getName());
		if (message.equals("")) {
			return false;
		}
		return notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(), message,
				order.getTeacher().getId(), order.getTeacher().getUsername(), notificationService);
	}

	private static boolean notifyTeacher(String phone, String email, String message, long teacherId,
										 String teacherUserName, NotificationService notificationService) {
		String m1 = message + "(<a href=\"http://www.1yingli.cn/myStudent\">管理订单</a>)";
		String m2 = message + "(http://www.1yingli.cn/myStudent)";
		if (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)) {
			SendMessageUtil.sendMessage(phone, m2);
		}
		if (CheckUtil.checkEmail(email)) {
			SendMailUtil.sendMessage(email, "订单状态改变通知", m1);
		}
		// web
		sendNotification(teacherId, notificationService, m1);
		// mobile
		String[] usernames = {teacherUserName};
		CloudPushUtil.IOSpushMessageToAccount(usernames, message);
		CloudPushUtil.IOSpushNoticeToAccount(usernames, message);
		return true;
	}

	public static boolean notifyManager(String msg) {
		try {
			WebNotificationUtil.sendMsgByGroup("manager", msg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Notification sendNotification(long teacherId, NotificationService notificationService, String msg) {
		Notification notification = new Notification();
		notification.setKind(0);
		notification.setRead(false);
		notification.setTitle(msg);
		notification.setUrl("#");
		notification.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		notification.setContent("");
		notificationService.saveWithTeacher(notification, teacherId);
		return notification;
	}

	public static Notification sendNotification(User user, NotificationService notificationService, String msg) {
		Notification notification = new Notification();
		notification.setKind(0);
		notification.setRead(false);
		notification.setTitle(msg);
		notification.setUrl("#");
		notification.setToUser(user);
		notification.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		notification.setContent("");
		notificationService.save(notification);
		return notification;
	}

	public static Notification sendNotification(User user, NotificationDao notificationDao, String msg) {
		Notification notification = new Notification();
		notification.setRead(false);
		notification.setKind(0);
		notification.setTitle(msg);
		notification.setUrl("#");
		notification.setToUser(user);
		notification.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		notification.setContent("");
		notificationDao.save(notification);
		return notification;
	}
}
