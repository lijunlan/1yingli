package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.ManagerMark;

public interface ManagerMarkDao {
	
	void save(ManagerMark managerMark);

	void remove(ManagerMark managerMark);

	void remove(String UUID);

	void update(ManagerMark managerMark);

	void update(String managerId, String UUID);

	ManagerMark queryUUID(long managerId);

	ManagerMark query(String UUID);

	Manager queryManager(String UUID);
}
