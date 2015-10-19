package cn.yiyingli.Service.Impl;

import java.util.Calendar;

import cn.yiyingli.Dao.ManagerDao;
import cn.yiyingli.Dao.ManagerMarkDao;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.ManagerMark;
import cn.yiyingli.Service.ManagerMarkService;

public class ManagerMarkServiceImpl implements ManagerMarkService {

	private ManagerMarkDao managerMarkDao;

	private ManagerDao managerDao;

	public ManagerMarkDao getManagerMarkDao() {
		return managerMarkDao;
	}

	public void setManagerMarkDao(ManagerMarkDao managerMarkDao) {
		this.managerMarkDao = managerMarkDao;
	}

	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	@Override
	public void save(ManagerMark managerMark) {
		getManagerMarkDao().save(managerMark);
	}

	@Override
	public void save(String managerId, String UUID) {
		Manager manager = getManagerDao().query(Long.valueOf(managerId));
		ManagerMark mm = getManagerMarkDao().queryUUID(Long.valueOf(managerId));
		if (mm == null) {
			ManagerMark managerMark = new ManagerMark();
			managerMark.setManager(manager);
			managerMark.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			managerMark.setUuid(UUID);
			save(managerMark);
		} else {
			getManagerMarkDao().update(managerId, UUID);
		}
	}

	@Override
	public void remove(ManagerMark managerMark) {
		getManagerMarkDao().remove(managerMark);
	}

	@Override
	public void remove(String UUID) {
		getManagerMarkDao().remove(UUID);
	}

	@Override
	public ManagerMark query(String UUID) {
		return getManagerMarkDao().query(UUID);
	}

	@Override
	public Manager queryManager(String UUID) {
		return getManagerMarkDao().queryManager(UUID);
	}

}
