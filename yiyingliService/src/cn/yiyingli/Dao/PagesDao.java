package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Persistant.Passage;

public interface PagesDao {

	void save(Pages pages);

	void remove(Pages pages);

	void update(Pages pages);

	Pages query(Long id);

	Pages queryByKey(String activityKey);

	long queryTeamSum();

	List<Pages> queryList(int page, int pageSize);

	List<Pages> queryListOrderByWeight(int page, int pageSize);

	List<Pages> queryListOrderByMile();

	List<Passage> queryTeacherPassageListById(long id, int page, int pageSize);


}
