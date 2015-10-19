package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetNotificationListService extends MsgService {

	private ManagerMarkService managerMarkService;

	private NotificationService notificationService;

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

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("page");
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		String page = (String) getData().get("page");
		List<Notification> notifications = getNotificationService().queryList(Integer.valueOf(page), 10, true);
		List<String> exNotifications = new ArrayList<String>();
		for (Notification notification : notifications) {
			SuperMap map = new SuperMap();
			map.put("content", notification.getContent());
			map.put("createTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(notification.getCreateTime()))));
			map.put("title", notification.getTitle());
			map.put("url", notification.getUrl());
			map.put("userId", notification.getToUser().getId());
			exNotifications.add(map.finishByJson());
		}
		String json = Json.getJson(exNotifications);
		setResMsg(MsgUtil.getSuccessMap().put("data", json).finishByJson());

	}

}
