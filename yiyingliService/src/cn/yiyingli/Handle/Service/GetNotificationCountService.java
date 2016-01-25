package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Util.MsgUtil;

public class GetNotificationCountService extends UMsgService {

	private NotificationService notificationService;

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	public void doit() {
		User user = getUser();
		long count = getNotificationService().queryUnreadSumNo(user.getId());
		setResMsg(MsgUtil.getSuccessMap().put("noti_count", count).finishByJson());
	}

}
