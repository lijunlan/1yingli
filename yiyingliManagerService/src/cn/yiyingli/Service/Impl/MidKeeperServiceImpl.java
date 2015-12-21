package cn.yiyingli.Service.Impl;

import cn.yiyingli.Dao.MidKeeperDao;
import cn.yiyingli.Persistant.MidKeeper;
import cn.yiyingli.Service.MidKeeperService;

public class MidKeeperServiceImpl implements MidKeeperService{

	private MidKeeperDao midKeeperDao;
	
	public MidKeeperDao getMidKeeperDao() {
		return midKeeperDao;
	}

	public void setMidKeeperDao(MidKeeperDao midKeeperDao) {
		this.midKeeperDao = midKeeperDao;
	}

	@Override
	public void save(MidKeeper mk) {
		midKeeperDao.save(mk);
	}

	@Override
	public String find(String mid) {
		return midKeeperDao.find(mid);
	}

	@Override
	public void remove(String mid) {
		midKeeperDao.remove(mid);
	}

}
