package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Teacher;

public interface TeacherDao {

	void save(Teacher teacher);

	Long saveAndReturnId(Teacher teacher);

	void remove(Teacher teacher);

	void remove(long id);

	void removeAllTip(long teacherId);

	void removeUserLike(long teacherId, long userId);

	void merge(Teacher teacher);

	void update(Teacher teacher);

	void updateAddMile(long teacherId, long mile);

	void updateAddSubMile(long teacherId, long subMile);

	void updateFromSql(String sql);

	Teacher query(long id);

	Teacher queryWithUser(long id);

	void updateAddLookNumber(long teacherId, long number);

	Boolean queryCheckLikeUser(long teacherId, long userId);

	Boolean queryCheckMile(long teacherId, long subMile);

	Teacher queryAll(long id);

	Teacher queryWithServiceProList(long id);

	Teacher queryForUser(long id);

	Teacher queryForTeacher(long id);

	Teacher queryWithLikeUser(long teacherId);

	Teacher queryByUserId(long userid);

	Teacher queryByUserIdWithServicePro(long userid);

	Teacher queryByInvitationCode(String invitationCode);

	List<Teacher> queryByIds(long[] ids);

	List<Teacher> queryByNameOrUsername(String word);

	List<Teacher> queryLikeListByUserId(long userid, int page, int pageSize, boolean lazy);

	List<Teacher> queryList(int page, int pageSize, boolean lazy);

	List<Teacher> queryListOnservice(int page, int pageSize, boolean lazy);

	List<Teacher> queryListByKeyWord(String keyword, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByActivity(String activityKey, int page, int pageSize);

}
