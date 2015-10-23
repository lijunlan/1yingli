package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.User;

public interface UserService {

	public static final short TEACHER_STATE_ON_SHORT = 0;

	public static final short TEACHER_STATE_OFF_SHORT = 1;

	public static final short TEACHER_STATE_CHECKING_SHORT = 2;

	public static final int PAGE_SIZE_INT = 12;

	void save(User user) throws Exception;

	Long saveAndReturnId(User user);

	void remove(User user);

	void remove(long id);

	void update(User user);

	void updateWithTeacher(User user);

	void changePassword(long userId, String oldpwd, String newpwd) throws Exception;

	User queryByNo(String no, boolean lazy);

	User query(long id, boolean lazy);

	User queryWithTeacher(long id, boolean lazy);

	User query(String username, boolean lazy);

	User queryWithWeibo(String weiboNo, boolean lazy);

	User queryWithWeixin(String weixinNo, boolean lazy);

	User queryWithTeacher(String username, boolean lazy);

	List<User> queryList(int page, int pageSize, boolean lazy);

	List<User> queryListByForbid(int page, int pageSize, boolean forbid, boolean lazy);

	List<User> queryListByValidate(int page, int pageSize, boolean validate, boolean lazy);

	List<User> queryListByLevel(int page, int pageSize, short level, boolean lazy);

	List<User> queryListByTeacher(int page, int pageSize, short teacherState, boolean lazy);

	List<User> queryList(int page, boolean lazy);

	List<User> queryListByForbid(int page, boolean forbid, boolean lazy);

	List<User> queryListByValidate(int page, boolean validate, boolean lazy);

	List<User> queryListByLevel(int page, short level, boolean lazy);

	List<User> queryListByTeacher(int page, short teacherState, boolean lazy);

}
