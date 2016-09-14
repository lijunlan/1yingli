package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MCreateNotificationService extends MMsgService {

	private NotificationService notificationService;

	private UserService userService;

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("title") && getData().containsKey("userId");
	}

	@Override
	public void doit() {
		String title = (String) getData().get("title");
		String userId = (String) getData().get("userId");
		User user = getUserService().query(Long.valueOf(userId), false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		Notification notification = new Notification();
		notification.setTitle(title);
		notification.setToUser(user);
		notification.setContent("");
		notification.setCreateTime("" + Calendar.getInstance().getTimeInMillis());
		notification.setRead(false);
		notification.setUrl("#");
		getNotificationService().save(notification);
		setResMsg(MsgUtil.getSuccessMsg("notification has been send"));
	}

}
