package cn.yiyingli.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.TimeTaskDao;
import cn.yiyingli.Persistant.Task;

public class TimeTaskDaoImpl extends HibernateDaoSupport implements TimeTaskDao {

	@Override
	public void save(Task task) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTask(long time) {
		List<Task> taskList=new ArrayList<Task>();
		String hql="from Task t where t.taskEndTime<?";
		taskList=getHibernateTemplate().find(hql,time);
		return taskList ;

	}

	public void remove(Task task) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(task);
	}

	@Override
	public void update(Task task) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(task);
	}

}
