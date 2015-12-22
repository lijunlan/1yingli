package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProService {

	public static final String TAG_QUESTION = "question";

	public static final String TAG_DESCRIPTION = "description";

	public static final String TAG_PRICE = "price";

	public static final String TAG_PRICE_TEMP = "priceTemp";

	public static final String TAG_ID = "serviceProId";
	
	public static final String TAG_COUNT = "count";

	public static final String TAG_TITLE = "title";

	void save(ServicePro servicePro);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	ServicePro query(long id);

	List<ServicePro> queryList(long[] ids, long teacherId);
}
