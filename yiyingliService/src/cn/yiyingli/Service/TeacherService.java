package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;

public interface TeacherService {

	public static final short CHECK_STATE_CHECKING_SHORT = 0;

	public static final short CHECK_STATE_NONE_SHORT = 1;

	public static final short CHECK_STATE_SUCCESS_SHORT = 2;

	public static final int PAGE_SIZE_INT = 12;

	public static final int HOME_PAGE_SIZE = 12;

	public static final int SALE_PAGE_SIZE = 12;

	void save(Teacher teacher);

	void saveWithDetailInfo(Teacher teacher);

	Long saveAndReturnId(Teacher teacher);

	void remove(Teacher teacher);

	void remove(long id);

	void removeAllTip(long teacherId);

	void updateAddLookNumber(long teacherId, long number);

	void update(Teacher teacher, boolean refreshRecommend);

	void updateWithDetailInfo(Teacher teacher, boolean refreshRecommend);

	void updateWithUser(Teacher teacher, long userId, boolean refreshRecommend);

	void updateUserLike(Teacher teacher, User user);

	void updateUserUnlike(long teacherId, long userId);

	void updateStudyExp(Teacher teacher, boolean refreshRecommend);

	void updateWorkExp(Teacher teacher, boolean refreshRecommend);

	Teacher query(long id);

	Teacher queryWithUser(long id);

	Boolean queryCheckLikeUser(long teacherId, long userId);

	Teacher queryAll(long id);

	Teacher queryWithServiceProList(long id);

	Teacher queryWithLikeUser(long teacherId);

	Teacher queryByUserId(long userid);

	Teacher queryByUserIdWithServicePro(long userid);

	List<Teacher> queryByIds(List<Long> ids);

	List<Teacher> queryByNameOrUsername(String word);

	List<Teacher> queryListOnservice(int page, int pageSize, boolean lazy);

	List<Teacher> queryList(int page, int pageSize, boolean lazy);

	List<Teacher> queryList(int page, boolean lazy);

	List<Teacher> queryListByHomePage(int pageSize);

	List<Teacher> queryListBySale(int page, int pageSize);

	List<Teacher> queryListByHomePage();

	long queryListBySaleNo();

	List<Teacher> queryListBySale(int page);

	List<Teacher> queryByTipOrderByShow(int size, long tipMark, boolean lazy);

	List<Teacher> queryByTipOrderByShow(long tipMark, boolean lazy);

	List<Teacher> queryLikeListByUserId(long userid, int page, boolean lazy);

	List<Teacher> queryLikeListByUserId(long userid, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByKeyWord(String keyword, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByKeyWord(String keyword, int page, boolean lazy);

	List<Teacher> queryListByTip(int page, int pageSize, long tipMark, boolean lazy);

	List<Teacher> queryListByTip(int page, long tipMark, boolean lazy);

	List<Teacher> queryListByTipAndLastId(long lastId, int size, long tipMark, boolean lazy);

	List<Teacher> queryListByTipAndLastId(long lastId, long tipMark, boolean lazy);

	List<Teacher> queryListByTipAndFirstId(long firstId, int size, long tipMark, boolean lazy);

	List<Teacher> queryListByTipAndFirstId(long firstId, long tipMark, boolean lazy);

	List<Teacher> queryListByLevel(short level, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByLevel(short level, int page, boolean lazy);

	List<Teacher> queryListByTipAndLevel(int page, int pageSize, long tipMark, short level, boolean lazy);

	List<Teacher> queryListByTipAndLevel(int page, long tipMark, short level, boolean lazy);

}
