package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProService {

	public static final String TAG_QUESTION = "question";

	public static final String TAG_RESUME = "resume";

	public static final String TAG_SELECTTIME = "selectTime";

	public static final String TAG_ID = "serviceProId";

	public static final String TAG_COUNT = "count";

	void save(ServicePro servicePro);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	ServicePro query(long id);

	List<ServicePro> queryList(long[] ids, long teacherId);
}
