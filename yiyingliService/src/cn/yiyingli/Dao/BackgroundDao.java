package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Background;

public interface BackgroundDao {

	void save(Background background);

	Long saveAndReturnId(Background background);

	void remove(Background background);

	void remove(long id);

	void update(Background background);

	Background query(long id);

	List<Background> queryList();

	List<Background> queryList(int page, int pageSize);

}
