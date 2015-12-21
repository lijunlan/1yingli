package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.OperateDao;
import cn.yiyingli.Persistant.OperateRecord;
import cn.yiyingli.Service.OperateService;

public class OperateServiceImpl implements OperateService {
	
	public OperateDao operateDao;
	
	public OperateDao getOperateDao() {
		return operateDao;
	}

	public void setOperateDao(OperateDao operateDao) {
		this.operateDao = operateDao;
	}

	@Override
	public void save(OperateRecord or) {
		// TODO Auto-generated method stub
		operateDao.save(or);
	}

	@Override
	public OperateRecord find(Long id) {
		// TODO Auto-generated method stub
		return operateDao.find(id);
	}

	@Override
	public List<OperateRecord> find(String name) {
		// TODO Auto-generated method stub
		return operateDao.find(name);
	}

	@Override
	public List<OperateRecord> find(String name, long beginTime, long endTime) {
		// TODO Auto-generated method stub
		return operateDao.find(name, beginTime, endTime);
	}

	@Override
	public List<OperateRecord> find(long begintime, long endTime) {
		// TODO Auto-generated method stub
		return operateDao.find(begintime, endTime);
	}

}
