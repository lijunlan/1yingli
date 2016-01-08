package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProService {

	public static final String TAG_QUESTION = "question";

	public static final String TAG_RESUME = "resume";

	public static final String TAG_SELECTTIME = "selectTime";

	public static final String TAG_ID = "serviceProId";

	public static final String TAG_COUNT = "count";

	public static final short STATE_CHECKING = 0;

	public static final short STATE_OK = 1;

	public static final int PAGE_SIZE = 12;

	public static final int HOMEPAGE_PAGE_SIZE = 6;

	public static final int SALEPAGE_PAGE_SIZE = 12;

	void save(ServicePro servicePro);

	void saveAndPlusNumber(ServicePro servicePro, boolean byManager);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	void updateAndPlusNumber(ServicePro servicePro, boolean remove);

	ServicePro query(long id);

	ServicePro queryByUser(long id);

	ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId);

	List<ServicePro> queryList(long[] ids, long teacherId);

	List<ServicePro> queryListByTeacherIdAndShow(long teacherId, boolean show, int page, int pageSize);

	List<ServicePro> queryListByTeacherIdAndShow(long teacherId, boolean show, int page);

	List<ServicePro> queryListByTeacherId(long teacherId, int page, int pageSize);

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

	long queryListBySaleNo();
}
