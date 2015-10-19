package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.ManagerDao;
import cn.yiyingli.Persistant.Manager;

public class ManagerDaoImpl extends HibernateDaoSupport implements ManagerDao {

	@Override
	public void save(Manager manager) {
		getHibernateTemplate().save(manager);
	}

	@Override
	public Long saveAndReturnId(Manager manager) {
		getHibernateTemplate().save(manager);
		return manager.getId();
	}

	@Override
	public void remove(Manager manager) {
		getHibernateTemplate().delete(manager);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Manager m where m.id=?", id);
	}

	@Override
	public void update(Manager manager) {
		getHibernateTemplate().update(manager);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Manager query(long id) {
		String hql = "from Manager m where m.id=?";
		@SuppressWarnings("unchecked")
		List<Manager> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Manager query(String username) {
		String hql = "from Manager m where m.username=?";
		@SuppressWarnings("unchecked")
		List<Manager> list = getHibernateTemplate().find(hql, username);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Manager> queryList() {
		String hql = "from Manager m";
		@SuppressWarnings("unchecked")
		List<Manager> list = getHibernateTemplate().find(hql);
		return list;
	}

}
