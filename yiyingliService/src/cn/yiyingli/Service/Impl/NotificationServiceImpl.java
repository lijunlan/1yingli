package cn.yiyingli.Service.Impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.yiyingli.Dao.NotificationDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Dao.UserMarkDao;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserMark;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Util.WebNotificationUtil;

public class NotificationServiceImpl implements NotificationService {

	private NotificationDao notificationDao;

	private UserMarkDao userMarkDao;

	private UserDao userDao;

	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	public UserMarkDao getUserMarkDao() {
		return userMarkDao;
	}

	public void setUserMarkDao(UserMarkDao userMarkDao) {
		this.userMarkDao = userMarkDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(Notification notification) {
		getNotificationDao().save(notification);
		long userId = notification.getToUser().getId();
		UserMark userMark = getUserMarkDao().queryUUID(userId);
		if (userMark != null) {
			try {
				String data = "{'title':'" + notification.getTitle() + "','url':'" + notification.getUrl() + "'}";
				WebNotificationUtil.sendMsgBySingle(userMark.getUuid(), data);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveWithTeacher(Notification notification, long teacherId) {
		User user = getUserDao().queryByTeacherId(teacherId);
		notification.setToUser(user);
		getNotificationDao().save(notification);
		long userId = notification.getToUser().getId();
		UserMark userMark = getUserMarkDao().queryUUID(userId);
		if (userMark != null) {
			try {
				String data = "{'title':'" + notification.getTitle() + "','url':'" + notification.getUrl() + "'}";
				WebNotificationUtil.sendMsgBySingle(userMark.getUuid(), data);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Long saveAndReturnId(Notification notification) {
		return getNotificationDao().saveAndReturnId(notification);
	}

	@Override
	public void remove(Notification notification) {
		getNotificationDao().remove(notification);
	}

	@Override
	public void remove(long id) {
		getNotificationDao().remove(id);
	}

	@Override
	public void update(Notification notification) {
		getNotificationDao().update(notification);
	}

	@Override
	public void updateReadByIds(long[] ids) {
		getNotificationDao().updateReadByIds(ids);
	}

	@Override
	public Notification query(long id, boolean lazy) {
		return getNotificationDao().query(id, lazy);
	}

	@Override
	public long querySumNo() {
		return getNotificationDao().querySumNo();
	}

	@Override
	public long queryUnreadSumNo(long userId) {
		return getNotificationDao().queryUnreadSumNo(userId);
	}

	@Override
	public void updateReadAll(long userId) {
		getNotificationDao().updateReadAll(userId);
	}

	@Override
	public long querySumNo(long userId) {
		return getNotificationDao().querySumNo(userId);
	}

	@Override
	public List<Notification> queryList(int page, int pageSize, boolean lazy) {
		return getNotificationDao().queryList(page, pageSize, lazy);
	}

	@Override
	public List<Notification> queryListByUserId(long userId, int page, int pageSize, boolean lazy) {
		return getNotificationDao().queryListByUserId(userId, page, pageSize, lazy);
	}

}
