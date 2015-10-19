package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class GetNotificationService extends MsgService {

	private NotificationService notificationService;

	private UserMarkService userMarkService;

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && (getData().containsKey("page") || getData().containsKey("notiId"));
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		if (getData().get("page") != null) {
			int page = 0;
			SuperMap toSend = MsgUtil.getSuccessMap();
			if (getData().get("page").equals("max")) {
				long count = getNotificationService().querySumNo(user.getId());
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
					setResMsg(MsgUtil.getErrorMsg("page is wrong"));
					return;
				}
				if (page <= 0) {
					setResMsg(MsgUtil.getErrorMsg("page is wrong"));
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
				setResMsg(MsgUtil.getErrorMsg("notiId is wrong"));
				return;
			}
			Notification notification = getNotificationService().query(nid, false);
			if(notification==null){
				setResMsg(MsgUtil.getErrorMsg("notification is not existed"));
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
