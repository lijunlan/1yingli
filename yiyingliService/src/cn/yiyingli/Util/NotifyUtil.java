package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.yiyingli.BaichuanTaobaoUtil.CloudPushUtil;
import cn.yiyingli.Dao.NotificationDao;
import cn.yiyingli.Persistant.*;
import cn.yiyingli.Service.NotificationService;

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
//		//Todo 根据手机号来发送短信
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
			String message = "尊敬的用户，订单号为" + phoneMap.get(phoneNum) + "的订单组已经付款完成，请等待导师接受订单"
					+ "(http://www.1yingli.cn/myTutor)";
			if (CheckUtil.checkMobileNumber(phoneNum) || CheckUtil.checkGlobleMobileNumber(phoneNum)) {
				SendMessageUtil.sendMessage(phoneNum, message);
			}
		}
		for (String email : emailMap.keySet()) {
			String message = "尊敬的用户，订单号为" + emailMap.get(email) + "的订单组已经付款完成，请等待导师接受订单"
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
			String message = "尊敬的导师，订单号为" + phoneMap.get(phoneNum) + "的订单组已经付款完成，等待您的接受"
					+ "(http://www.1yingli.cn/myStudent)";
			if (CheckUtil.checkMobileNumber(phoneNum) || CheckUtil.checkGlobleMobileNumber(phoneNum)) {
				SendMessageUtil.sendMessage(phoneNum, message);
			}
		}
		for (String email : emailMap.keySet()) {
			String message = "尊敬的导师，订单号为" + emailMap.get(email) + "的订单组已经付款完成，等待您的接受"
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
			String message = "尊敬的导师，订单号为" + orderNo + "的订单已经追加付款"
					+ additionalPay.getMoney() + "(http://www.1yingli.cn/myTutor)";
			if (CheckUtil.checkMobileNumber(teacherPhone) || CheckUtil.checkGlobleMobileNumber(teacherPhone)) {
				SendMessageUtil.sendMessage(teacherPhone, message);
			}
			message = "尊敬的导师，订单号为" + orderNo + "的订单已经追加付款"
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

	public static boolean notifyTeacher(Order order, String message, NotificationService notificationService) {
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
		notification.setTitle(msg);
		notification.setUrl("#");
		notification.setToUser(user);
		notification.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		notification.setContent("");
		notificationDao.save(notification);
		return notification;
	}
}
