package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Pages;

public interface PagesService {

	public static final String KEY_HOME_TEACHER = "homeTeacher";

	public static final String KEY_SALE_TEACHER = "saleTeacher";
	
	public static final String KEY_HOME_SERVICEPRO = "homeServicePro";

	public static final String KEY_SALE_SERVICEPRO = "saleServicePro";

	public static final String KEY_SALE_PASSAGE = "salePassage";

	public static final int SIZE_MANAGER_PAGE = 10;

	void save(Pages pages);

	void remove(Pages pages);

	void update(Pages pages);

	Pages query(Long id);

	Pages queryByKey(String activityKey);

	List<Pages> queryList(int page, int pageSize);

	List<Pages> queryList(int page);

	List<Pages> queryListOrderByWeight(int page,int pageSize);

	List<Pages> queryListOrderByMile();
}
