package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Manager;

public interface ManagerDao {
	
	void save(Manager manager);

	Long saveAndReturnId(Manager manager);

	void remove(Manager manager);

	void remove(long id);

	void update(Manager manager);

	void updateFromSql(String sql);

	Manager query(long id);
	
	Manager query(String username);

	List<Manager> queryList();
}
