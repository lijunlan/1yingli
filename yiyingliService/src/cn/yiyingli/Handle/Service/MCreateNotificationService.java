package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MCreateNotificationService extends MsgService {

	private ManagerMarkService managerMarkService;

	private NotificationService notificationService;
	
	private UserService userService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

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
		return getData().containsKey("mid")&&getData().containsKey("title")&&getData().containsKey("userId");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		String title = (String) getData().get("title");
		String userId = (String) getData().get("userId");
		User user = getUserService().query(Long.valueOf(userId), false);
		if(user==null){
			setResMsg(MsgUtil.getErrorMsg("user is not existed"));
			return;
		}
		Notification notification = new Notification();
		notification.setTitle(title);
		notification.setToUser(user);
		notification.setContent("");
		notification.setCreateTime(""+Calendar.getInstance().getTimeInMillis());
		notification.setRead(false);
		notification.setUrl("#");
		getNotificationService().save(notification);
		setResMsg(MsgUtil.getSuccessMsg("notification has been send"));
	}

}
