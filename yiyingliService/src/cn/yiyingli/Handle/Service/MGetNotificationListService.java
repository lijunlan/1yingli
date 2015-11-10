package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetNotificationListService extends MMsgService {

	private NotificationService notificationService;

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {
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
