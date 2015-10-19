package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.Teacher;

public interface NotificationService {

	public static final int PAGE_SIZE_INT = 9;

	void save(Notification notification);

	void saveWithTeacher(Notification notification, Teacher teacher);

	Long saveAndReturnId(Notification notification);

	void remove(Notification notification);

	void remove(long id);

	void update(Notification notification);

	long querySumNo();

	long querySumNo(long userId);

	Notification query(long id, boolean lazy);

	List<Notification> queryList(int page, int pageSize, boolean lazy);

	List<Notification> queryListByUserId(long userId, int page, int pageSize, boolean lazy);

}
