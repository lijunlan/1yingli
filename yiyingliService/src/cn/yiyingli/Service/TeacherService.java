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

	public static final int SHOW_PAGE_SIZE = 10;

	void save(Teacher teacher);

	void saveWithDetailInfo(Teacher teacher);

	Long saveAndReturnId(Teacher teacher);

	void remove(Teacher teacher);

	void remove(long id);

	void removeAllTip(long teacherId);

	void updateAddLookNumber(long teacherId, long number);

	void update(Teacher teacher, boolean refreshRecommend);

	void updateAddMile(long teacherId, float mile);

	boolean updateAddSubMile(long teacherId, long subMile);

	void updateWithDetailInfo(Teacher teacher, boolean refreshRecommend);

	void updateWithUser(Teacher teacher, long userId, boolean refreshRecommend);

	boolean updateUserLike(Teacher teacher, User user);

	boolean updateUserUnlike(long teacherId, long userId);

	void updateStudyExp(Teacher teacher, boolean refreshRecommend);

	void updateWorkExp(Teacher teacher, boolean refreshRecommend);

	Teacher query(long id);

	Teacher queryWithOutStatue(long id);

	Teacher queryWithUser(long id);

	Boolean queryCheckLikeUser(long teacherId, long userId);

	Teacher queryAll(long id);

	Teacher queryWithServiceProList(long id);

	Teacher queryForUser(long id);

	Teacher queryForTeacher(long id);

	Teacher queryByName(String name);

	Teacher queryWithLikeUser(long teacherId);

	Teacher queryByUserId(long userid);

	Teacher queryByUserIdWithServicePro(long userid);

	List<Teacher> queryByIds(List<Long> ids);

	List<Teacher> queryByNameOrUsername(String word);

	List<Teacher> queryListOnservice(int page, int pageSize, boolean lazy);

	List<Teacher> queryList(int page, int pageSize, boolean lazy);

	List<Teacher> queryList(int page, boolean lazy);

	List<Teacher> queryLikeListByUserId(long userid, int page, boolean lazy);

	List<Teacher> queryLikeListByUserId(long userid, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByKeyWord(String keyword, int page, int pageSize, boolean lazy);

	List<Teacher> queryListByKeyWord(String keyword, int page, boolean lazy);

	List<Teacher> queryListByActivity(String activityKey, int page, int pageSize);

}
