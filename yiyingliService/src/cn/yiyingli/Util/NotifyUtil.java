package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cn.yiyingli.BaichuanTaobaoUtil.CloudPushUtil;
import cn.yiyingli.Dao.NotificationDao;
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

	private static String getTeacherOrderMessageByState(Order order, String operation) {
		String states[] = order.getState().split(",");
		String No = order.getOrderNo();
		Teacher teacher = order.getTeacher();
		String nowState = states[0];
		if (operation.equals(MOrderSalaryDoneService.class.getName())) {
			return "尊敬的用户，订单(订单号:" + No + "),酬劳已成功转出，请注意查收.如有疑问请咨询客服.";
		}
		if (states.length > 1) {
			String lastState = states[1];
			if (nowState.equals(OrderService.ORDER_STATE_USER_REGRET)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + "),客户申请退款，等待您的确认，请在5天内到平台进行操作，超时系统会自动同意退款。";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_REGRET)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				if (operation.equals(TRefuseOrderService.class.getName())) {
					// TODO
					return "尊敬的用户，订单(订单号:" + No + ")已被[" + teacher.getName() + "]拒绝,拒绝理由:" + order.getRefuseReason()
							+ ",您可预约其他优秀的导师哦,预付款将在24小时内退还到您的账户。";
				} else {
					return "尊敬的用户，订单(订单号:" + No + "),客户申请退款，等待您的确认。请在5天内到平台进行操作，超时系统会自动同意退款。";
				}
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + "),已经被用户取消。";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_DISLIKE)
					&& lastState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)) {
				return "尊敬的用户，订单(订单号:" + No + "),对本次服务不满,申请退款,等待您的回复,请您在五天内到平台进行操作,超时系统将自动同意退款.";
			} else if (nowState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				// return "尊敬的用户，订单(订单号:" + No + ")已自动确认服务完毕,请等待用户确认本次服务.";
				return "";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_DISLIKE)) {
				return "尊敬的用户，订单(订单号:" + No + "),由于您长时间未作出响应,系统已自动确认同意退款,如有疑问,请与客服联系.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_REGRET)) {
				return "尊敬的用户，订单(订单号:" + No + "),由于您长时间未作出响应,系统已自动确认同意退款,如有疑问,请与客服联系.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_COMMENT)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。您的酬劳会在24小时内到账.";
			} else if (nowState.equals(OrderService.ORDER_STATE_END_FAILED)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。您的酬劳会在24小时内到账.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：同意此次退款。";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")已被您接受,请及时与客户联系并确认服务时间,之后请到一英里平台登记服务时间。(客户姓名:" + order.getCustomerName()
						+ ",电话:" + order.getCustomerPhone() + ",邮箱:" + order.getCustomerEmail() + ",微信:"
						+ order.getCustomerContact() + ")";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")已被您接受,请及时为客户提供服务。(客户姓名:" + order.getCustomerName() + ",电话:"
						+ order.getCustomerPhone() + ",邮箱:" + order.getCustomerEmail() + ",微信:"
						+ order.getCustomerContact() + ")";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + ")已经进入等待服务状态,请在" + order.getOkTime() + "，按时进行服务.(客户姓名:"
						+ order.getCustomerName() + ",电话:" + order.getCustomerPhone() + ",邮箱:"
						+ order.getCustomerEmail() + ",微信:" + order.getCustomerContact() + ").系统会在约定时间("
						+ order.getOkTime() + ")2周后自动确认服务完毕.";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	private static String getUserOrderListMessageByState(String state, String No) {
		String states[] = state.split(",");
		String nowState = states[0];
		if (states.length > 1) {
			String lastState = states[1];
			if (nowState.equals(OrderListService.ORDER_STATE_CANCEL_PAID)
					&& lastState.equals(OrderListService.ORDER_STATE_NOT_PAID)) {
				return "尊敬的用户，订单(流水号:" + No + ")由于超时未支付,已被系统自动关闭,如有需要,请重新预约。";
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
				return "尊敬的用户，订单(订单号:" + No + ")已经申请取消,请等待[" + teacher.getName() + "]同意";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_REGRET)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				if (operation.equals(TRefuseOrderService.class.getName())) {
					return "尊敬的用户，订单(订单号:" + No + ")已被导师[" + teacher.getName() + "]拒绝,拒绝理由:" + order.getRefuseReason()
							+ ",您可预约其他优秀的导师哦,预付款将在24小时内退还到您的账户。";
				} else {
					return "尊敬的用户，订单(订单号:" + No + ")已经申请取消,请等待[" + teacher.getName() + "]同意";
				}
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")已经取消，我们将竭尽全力在24小时内为您全额退款";
			} else if (nowState.equals(OrderService.ORDER_STATE_USER_DISLIKE)
					&& lastState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)) {
				return "尊敬的用户,订单(订单号:" + No + ")已经申请退款,请等待[" + teacher.getName() + "]确认";
			} else if (nowState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				return "尊敬的用户,订单(订单号:" + No + "),[" + teacher.getName() + "]已经确认服务完毕.请到一英里平台确认本次服务完成.(系统会在1周后自动确认服务)";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_DISLIKE)) {
				return "尊敬的用户,订单(订单号:" + No + "),[" + teacher.getName() + "]已同意退款,我们将竭尽全力在24内退还到您的账户,请注意查收.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_USER_REGRET)) {
				return "尊敬的用户,订单(订单号:" + No + "),[" + teacher.getName() + "]已同意退款,我们将竭尽全力在24内退还到您的账户,请注意查收.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_COMMENT)
					&& lastState.equals(OrderService.ORDER_STATE_SERVICE_FINISH)) {
				return "尊敬的用户,订单(订单号:" + No + "),系统已经帮您自动确认本次服务,请到一英里平台对本次服务进行评价,留下您宝贵的意见.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_COMMENT)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。请到一英里平台对本次服务进行评价";
			} else if (nowState.equals(OrderService.ORDER_STATE_END_FAILED)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：拒绝此次退款。";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_MANAGER_IN)) {
				return "尊敬的用户，订单(订单号:" + No + "),客服已经协调结束，协调结果：同意此次退款。我们将在24小时内给您退款.";
			} else if (nowState.equals(OrderService.ORDER_STATE_END_FAILED)
					&& lastState.equals(OrderService.ORDER_STATE_RETURN_SUCCESS)) {
				return "尊敬的用户，订单(订单号:" + No + "),已经成功退款,请注意查收.如有疑问请咨询客服.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经取消服务,本次预付款将在24小时内为您退款。";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经接受,TA将通过您提供的联系方式与您联系,请等待TA与您确认服务时间。";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_FINISH_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已经接受,TA将通过您提供的联系方式与您联系,请等待服务。";
			} else if (nowState.equals(OrderService.ORDER_STATE_MANAGER_IN)
					&& (OrderService.ORDER_STATE_USER_DISLIKE.equals(lastState)
							|| OrderService.ORDER_STATE_USER_REGRET.equals(lastState))) {
				return "尊敬的用户，订单(订单号:" + No + ")的退款申请已被[" + teacher.getName() + "]拒绝,客服将介入此订单,请耐心等待,我们会在48小时内与您联系。";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_SERVICE)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + "),[" + teacher.getName() + "]已确认服务时间为(" + order.getOkTime() + "),请等待服务.";
			} else if (nowState.equals(OrderService.ORDER_STATE_WAIT_RETURN)
					&& lastState.equals(OrderService.ORDER_STATE_WAIT_ENSURETIME)) {
				return "尊敬的用户，订单(订单号:" + No + ")已经被[" + teacher.getName() + "]取消,您可以在平台选择其他优秀的导师进行服务。本次预付款将在24小时内为您退款。";
			} else if (nowState.equals(OrderService.ORDER_STATE_FINISH_PAID)
					&& lastState.equals(OrderService.ORDER_STATE_NOT_PAID)) {
				return "尊敬的用户，订单(订单号:" + No + ")，客户(" + order.getCustomerName() + ")已经付款，等待您的接受。";
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
		return notifyUserNormal(orderList.getCustomerPhone(), orderList.getCustomerEmail(), "订单状态改变通知", message,
				orderList.getUser(), notificationService);
	}

	/**
	 * @param phone
	 * @param email
	 * @param message
	 * @param uid
	 *            用户标识 UUID
	 */
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
		String[] usernames = { user.getUsername() };
		CloudPushUtil.IOSpushMessageToAccount(usernames, message);
		CloudPushUtil.IOSpushNoticeToAccount(usernames, message);
		return true;
	}

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
		String[] usernames = { teacherUserName };
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
