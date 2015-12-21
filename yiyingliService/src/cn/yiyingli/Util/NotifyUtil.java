package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cn.yiyingli.BaichuanTaobaoUtil.CloudPushUtil;
import cn.yiyingli.Dao.NotificationDao;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;

public class NotifyUtil {

	/**
	 * @param phone
	 * @param email
	 * @param message
	 * @param uid
	 *            用户标识 UUID
	 */
	public static boolean notifyUserOrder(String phone, String email, String message, User user,
			NotificationService notificationService) {
		return notifyUserNormal(phone, email, "订单状态改变通知", message, user, notificationService);
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
		String m1 = message + "(<a href=\"http://www.1yingli.cn/#!/myTutor\">查看订单</a>)";
		String m2 = message + "(http://www.1yingli.cn/#!/myTutor)";
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

	public static boolean notifyBD(String msg) {
		SendMailUtil.sendMessage("boom@1yingli.cn", "订单状态通知", msg);
		return true;
	}

	public static boolean notifyTeacher(String phone, String email, String message, Teacher teacher,
			NotificationService notificationService) {
		String m1 = message + "(<a href=\"http://www.1yingli.cn/#!/myStudent\">管理订单</a>)";
		String m2 = message + "(http://www.1yingli.cn/#!/myStudent)";
		if (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)) {
			SendMessageUtil.sendMessage(phone, m2);
		}
		if (CheckUtil.checkEmail(email)) {
			SendMailUtil.sendMessage(email, "订单状态改变通知", m1);
		}
		// web
		sendNotification(teacher, notificationService, m1);
		// mobile
		String[] usernames = { teacher.getUsername() };
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

	public static Notification sendNotification(Teacher teacher, NotificationService notificationService, String msg) {
		Notification notification = new Notification();
		notification.setRead(false);
		notification.setTitle(msg);
		notification.setUrl("#");
		notification.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		notification.setContent("");
		notificationService.saveWithTeacher(notification, teacher);
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
