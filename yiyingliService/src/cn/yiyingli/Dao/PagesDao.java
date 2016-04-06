package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Pages;

public interface PagesDao {

	void save(Pages pages);

	void remove(Pages pages);

	void update(Pages pages);

	Pages query(Long id);

	Pages queryByKey(String activityKey);

	List<Pages> queryList(int page, int pageSize);

	List<Pages> queryListOrderByWeight(int page, int pageSize);

}
