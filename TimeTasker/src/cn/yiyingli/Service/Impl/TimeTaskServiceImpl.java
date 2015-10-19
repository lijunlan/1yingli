package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.TimeTaskDao;
import cn.yiyingli.Persistant.Task;
import cn.yiyingli.Service.TimeTaskService;

public class TimeTaskServiceImpl implements TimeTaskService {

	private TimeTaskDao timeTaskDao;
	
	public TimeTaskDao getTimeTaskDao() {
		return timeTaskDao;
	}

	public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
		this.timeTaskDao = timeTaskDao;
	}

	@Override
	public void setTask(Task task) {
		// TODO Auto-generated method stub
		timeTaskDao.save(task);
	}

	@Override
	public List<Task> patrol(long time) {
		// TODO Auto-generated method stub
		return timeTaskDao.getTask(time);
	}

	@Override
	public void finishTask(Task task) {
		// TODO Auto-generated method stub
		timeTaskDao.remove(task);
	}

	@Override
	public void reSaveTask(Task task) {
		// TODO Auto-generated method stub
		timeTaskDao.update(task);
	}
	

}
