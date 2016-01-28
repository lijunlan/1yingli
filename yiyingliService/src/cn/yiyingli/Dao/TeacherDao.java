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

	void updateFromSql(String sql);

	void updateAddLookNumber(long teacherId, long number);

	Teacher query(long id, boolean lazy);

	Teacher queryWithUser(long id, boolean lazy);

	Boolean queryCheckLikeUser(long teacherId, long userId);

	Teacher queryAll(long id);

	// Teacher queryWithTips(long id, boolean lazy);

	Teacher queryForUser(long id, boolean lazy);

	Teacher queryForTeacher(long id, boolean lazy);

	Teacher queryWithLikeUser(long teacherId, boolean lazy);

	Teacher queryByUserId(long userid, boolean lazy);

	Teacher queryByUserIdWithTService(long userid, boolean lazy);

	List<Teacher> queryByIds(long[] ids);

	List<Teacher> queryByNameOrUsername(String word);

	List<Teacher> queryLikeListByUserId(long userid, int page, int pageSize, boolean lazy);

	List<Teacher> queryList(int page, int pageSize, boolean lazy);

	List<Teacher> queryListByHomePage(int pageSize);

	long queryListBySaleNo();

	List<Teacher> queryListBySale(int page, int pageSize);

	List<Teacher> queryListOnservice(int page, int pageSize, boolean lazy);

	List<Teacher> queryByTipOrderByShow(int size, long tipMark, boolean lazy);

	List<Teacher> queryListByKeyWord(String keyword, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByTip(int page, int pageSize, long tipMark, boolean lazy);

	List<Teacher> queryListByTipOrderByLikeNo(int page, int pageSize, long tipMark, boolean lazy);

	List<Teacher> queryListByTipAndLastId(long lastId, int size, long tipMark, boolean lazy);

	List<Teacher> queryListByTipAndFirstId(long firstId, int size, long tipMark, boolean lazy);

	List<Teacher> queryListByLevel(short level, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByTipAndLevel(int page, int pageSize, long tipMark, short level, boolean lazy);


}
