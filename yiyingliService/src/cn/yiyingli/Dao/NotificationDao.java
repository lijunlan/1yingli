package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Notification;

public interface NotificationDao {

	void save(Notification notification);

	Long saveAndReturnId(Notification notification);

	void remove(Notification notification);

	void remove(long id);

	void update(Notification notification);

	void updateFromSql(String sql);

	void updateReadAll(long userId, int kind);

	void updateReadByIds(long[] ids);
	
	long querySumNo();
	
	long queryUnreadSumNo(long userId);

	long querySumNo(long userId, int kind);

	Notification query(long id, boolean lazy);

	List<Notification> queryList(int page, int pageSize, boolean lazy);

	List<Notification> queryListByUserId(long userId, int page, int pageSize,int kind, boolean lazy);




}
