package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.ManagerDao;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.ManagerService;

public class ManagerServiceImpl implements ManagerService {

	private ManagerDao managerDao;

	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	@Override
	public void save(Manager manager) {
		getManagerDao().save(manager);
	}

	@Override
	public Long saveAndReturnId(Manager manager) {
		return getManagerDao().saveAndReturnId(manager);
	}

	@Override
	public void remove(Manager manager) {
		getManagerDao().remove(manager);
	}

	@Override
	public void remove(long id) {
		getManagerDao().remove(id);
	}

	@Override
	public void update(Manager manager) {
		getManagerDao().update(manager);
	}

	@Override
	public Manager query(long id) {
		return getManagerDao().query(id);
	}

	@Override
	public Manager query(String username) {
		return getManagerDao().query(username);
	}

	@Override
	public List<Manager> queryList() {
		return getManagerDao().queryList();
	}

}
