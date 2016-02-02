package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserLikePassage;
import cn.yiyingli.Persistant.UserLikeTeacher;

public interface UserDao {

	void save(User user);

	void saveAndCount(User user, Distributor distributor);

	Long saveAndReturnId(User user);

	void remove(User user);

	void remove(long id);

	void merge(User user);

	void update(User user);

	void updateUsername(User user);

	void updateLikeTeacher(UserLikeTeacher userLikeTeacher);

	void updateLikePassage(UserLikePassage userLikePassage);

	void updateFromSql(String sql);

	User queryByNo(String no, boolean lazy);

	User query(long id, boolean lazy);

	User queryWithTeacher(long id, boolean lazy);

	User queryByTeacherId(long teacherId);

	User query(String username, boolean lazy);

	User queryWithWeixinPlatform(String weixinNo);

	User queryWithWeibo(String weiboNo, boolean lazy);

	User queryWithWeixin(String weixinNo, boolean lazy);

	User queryWithTeacher(String username, boolean lazy);

	List<User> queryList(int page, int pageSize, boolean lazy);

	List<User> queryListByForbid(int page, int pageSize, boolean forbid, boolean lazy);

	List<User> queryListByValidate(int page, int pageSize, boolean validate, boolean lazy);

	List<User> queryListByLevel(int page, int pageSize, short level, boolean lazy);

	List<User> queryListByTeacher(int page, int pageSize, short teacherState, boolean lazy);

}
