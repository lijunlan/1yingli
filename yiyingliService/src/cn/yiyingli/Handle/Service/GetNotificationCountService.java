package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class GetNotificationCountService extends MsgService {

	private UserMarkService userMarkService;

	private NotificationService notificationService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		int page = 0;
		long count = getNotificationService().querySumNo(user.getId());
		if (count % NotificationService.PAGE_SIZE_INT > 0)
			page = (int) (count / NotificationService.PAGE_SIZE_INT) + 1;
		else
			page = (int) (count / NotificationService.PAGE_SIZE_INT);
		if (page == 0)
			page = 1;
		setResMsg(MsgUtil.getSuccessMap().put("noti_count", count).put("page", page).finishByJson());
	}

}
