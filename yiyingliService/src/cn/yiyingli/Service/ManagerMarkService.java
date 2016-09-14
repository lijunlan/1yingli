package cn.yiyingli.Service;

import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.ManagerMark;

public interface ManagerMarkService {

	void save(ManagerMark managerMark);

	void save(String managerId, String UUID);

	void remove(ManagerMark managerMark);

	void remove(String UUID);

	ManagerMark query(String UUID);

	Manager queryManager(String UUID);

}
