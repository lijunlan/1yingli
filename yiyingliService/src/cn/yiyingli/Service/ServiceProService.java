package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;

public interface ServiceProService {

	public static final String TAG_ID = "serviceProId";

	public static final short TALK_WAY_ONLINE = 0;

	public static final short TALK_WAY_OFFLINE = 1;

	public static final short STATE_CHECKING = 0;

	public static final short STATE_OK = 1;

	public static final short STATE_FAILED = 2;

	public static final int KIND_CONSULTATION = 1;

	public static final int KIND_EXPERIENCE = 2;

	public static final int KIND_MODIFY = 3;

	public static final int KIND_TEACH = 4;

	public static final int KIND_CUSTOMIZE = 5;

	public static final int PAGE_SIZE = 3;

	public static final int ABOUT_PAGE_SIZE = 2;

	public static final int TEACHER_PAGE_SIZE = 8;

	public static final int USER_PAGE_SIZE = 12;

	public static final int MANAGER_PAGE_SIZE = 12;

	public static final int HOMEPAGE_PAGE_SIZE = 12;

	public static final int SALEPAGE_PAGE_SIZE = 12;

	public static final int KIND_PAGE_SIZE = 10;

	void save(ServicePro servicePro);

	void saveAndPlusNumber(ServicePro servicePro, boolean byManager);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	void updateUserUnlike(long serviceProId, long userId);

	boolean updateUserLike(ServicePro servicePro, User user);

	void updateAndPlusNumber(ServicePro servicePro, boolean remove);

	ServicePro query(long id);

	ServicePro queryByUser(long id);

	ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId);

	List<ServicePro> queryList(int page);

	Boolean queryCheckLikeUser(long serviceProId, long userId);

	List<ServicePro> queryList(int page, int pageSize);

	List<ServicePro> queryList(long[] ids, long teacherId);

	List<ServicePro> queryListByTeacherIdAndShow(long teacherId, boolean show, int page, int pageSize);

	List<ServicePro> queryListByTeacherIdAndShow(long teacherId, boolean show, int page);

	List<ServicePro> queryListByTeacherId(long teacherId, int page, int pageSize);

	List<ServicePro> queryListByState(short state, int page);

	List<ServicePro> queryListByState(short state, int page, int pageSize);

	List<ServicePro> queryListByTeacherId(long teacherId, int page);

	List<ServicePro> queryListByTeacherIdAndShowAndState(long teacherId, boolean show, short state, int page,
			int pageSize);

	List<ServicePro> queryListByTeacherIdAndShowAndState(long teacherId, boolean show, short state, int page);

	List<ServicePro> queryListByTeacherIdAndState(long teacherId, short state, int page, int pageSize);

	List<ServicePro> queryListByTeacherIdAndState(long teacherId, short state, int page);

	List<ServicePro> queryListByHomePage(int pageSize);

	List<ServicePro> queryListByHomePage();

	List<ServicePro> queryListBySale(int page, int pageSize);

	List<ServicePro> queryListBySale(int page);

	List<ServicePro> queryListByKind(int kind, int page, int pageSize);

	List<ServicePro> queryListByKind(int kind);

	List<ServicePro> queryListByTeacherIdForUser(long teacherId, int page, int pageSize);

	List<ServicePro> queryListOtherByTeacher(long serviceProId, long teacherId, int page, int pageSize);

	List<ServicePro> queryListOtherByTeacher(long serviceProId, long teacherId, int page);

	List<ServicePro> queryLikeListByUserId(long userId, int page, int pageSize);

	List<ServicePro> queryLikeListByUserId(long userId, int page);

	long queryListBySaleNo();

}
