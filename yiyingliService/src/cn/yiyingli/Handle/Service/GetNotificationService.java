package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class GetNotificationService extends UMsgService {

	private NotificationService notificationService;

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && (getData().containsKey("page") || getData().containsKey("notiId"));
	}

	@Override
	public void doit() {
		User user = getUser();
		if (getData().get("page") != null) {
			long count = getNotificationService().querySumNo(user.getId());
			int page = 0;
			SuperMap toSend = MsgUtil.getSuccessMap();
			toSend.put("count", count);
			if (getData().get("page").equals("max")) {
				if (count % NotificationService.PAGE_SIZE_INT > 0)
					page = (int) (count / NotificationService.PAGE_SIZE_INT) + 1;
				else
					page = (int) (count / NotificationService.PAGE_SIZE_INT);
				if (page == 0)
					page = 1;
				toSend.put("page", page);
			} else {
				try {
					page = Integer.parseInt((String) getData().get("page"));
				} catch (Exception e) {
					setResMsg(MsgUtil.getErrorMsgByCode("12010"));
					return;
				}
				if (page <= 0) {
					setResMsg(MsgUtil.getErrorMsgByCode("12010"));
					return;
				}
			}
			List<Notification> notifications = getNotificationService().queryListByUserId(user.getId(), page, 9, false);
			List<String> sends = new ArrayList<String>();
			for (Notification n : notifications) {
				SuperMap map = new SuperMap();
				map.put("title", n.getTitle());
				map.put("notiId", n.getId());
				map.put("url", n.getUrl());
				map.put("time", n.getCreateTime());
				sends.add(map.finishByJson());
			}
			setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
		} else {
			String notiId = (String) getData().get("notiId");
			long nid = 0;
			try {
				Long.parseLong(notiId);
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsgByCode("12011"));
				return;
			}
			Notification notification = getNotificationService().query(nid, false);
			if (notification == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("12012"));
				return;
			}
			SuperMap map = new SuperMap();
			map.put("title", notification.getTitle());
			map.put("content", notification.getContent());
			map.put("createTime", notification.getCreateTime());
			map.put("notiId", notification.getId());
			setResMsg(MsgUtil.getSuccessMsg(map.finishByJson()));
		}

	}

}
