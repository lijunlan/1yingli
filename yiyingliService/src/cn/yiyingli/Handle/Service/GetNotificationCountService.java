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
