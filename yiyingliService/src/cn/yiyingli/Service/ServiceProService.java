package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProService {

	public static final String TAG_QUESTION = "question";

	public static final String TAG_RESUME = "resume";

	public static final String TAG_SELECTTIME = "selectTime";

	public static final String TAG_ID = "serviceProId";

	public static final String TAG_COUNT = "count";

	public static final short STYLE_TALK = 0;

	public static final short STYLE_SERVICE = 1;

	public static final short STATE_CHECKING = 0;

	public static final short STATE_OK = 1;

	void save(ServicePro servicePro);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	ServicePro query(long id);

	ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId);

	List<ServicePro> queryList(long[] ids, long teacherId);
}
