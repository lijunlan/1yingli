package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Manager;

public interface ManagerService {

	void save(Manager manager);

	Long saveAndReturnId(Manager manager);

	void remove(Manager manager);

	void remove(long id);

	void update(Manager manager);

	Manager query(long id);
	
	Manager query(String username);

	List<Manager> queryList();

}
