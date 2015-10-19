package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.FreeTimeDao;
import cn.yiyingli.Persistant.FreeTime;
import cn.yiyingli.Service.FreeTimeService;

public class FreeTimeServiceImpl implements FreeTimeService {

	private FreeTimeDao freeTimeDao;

	public FreeTimeDao getFreeTimeDao() {
		return freeTimeDao;
	}

	public void setFreeTimeDao(FreeTimeDao freeTimeDao) {
		this.freeTimeDao = freeTimeDao;
	}

	@Override
	public void save(FreeTime freeTime) {
		getFreeTimeDao().save(freeTime);
	}

	@Override
	public Long saveAndReturnId(FreeTime freeTime) {
		return getFreeTimeDao().saveAndReturnId(freeTime);
	}

	@Override
	public void remove(FreeTime freeTime) {
		getFreeTimeDao().remove(freeTime);
	}

	@Override
	public void remove(long id) {
		getFreeTimeDao().remove(id);
	}

	@Override
	public void removeAll(long teacherId) {
		getFreeTimeDao().removeAll(teacherId);
	}

	@Override
	public void update(FreeTime freeTime) {
		getFreeTimeDao().update(freeTime);
	}

	@Override
	public FreeTime query(long id, boolean lazy) {
		return getFreeTimeDao().query(id, lazy);
	}

	@Override
	public List<FreeTime> queryListByTeacherId(long teacherId, boolean lazy) {
		return getFreeTimeDao().queryListByTeacherId(teacherId, lazy);
	}

	@Override
	public List<FreeTime> queryListByTServiceId(long tServiceId, boolean lazy) {
		return getFreeTimeDao().queryListByTServiceId(tServiceId, lazy);
	}
}
