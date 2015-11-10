package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.CheckNoDao;
import cn.yiyingli.Persistant.CheckNo;

public class CheckNoDaoImpl extends HibernateDaoSupport implements CheckNoDao {

	@Override
	public void save(CheckNo checkNo) {
		getHibernateTemplate().save(checkNo);
	}

	@Override
	public Long saveAndReturnId(CheckNo checkNo) {
		getHibernateTemplate().save(checkNo);
		return checkNo.getId();
	}

	@Override
	public void remove(CheckNo checkNo) {
		getHibernateTemplate().delete(checkNo);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from CheckNo cn where cn.id=?", id);
	}

	@Override
	public void update(CheckNo checkNo) {
		getHibernateTemplate().update(checkNo);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public CheckNo query(long id) {
		String hql = "from CheckNo cn where cn.id=?";
		@SuppressWarnings("unchecked")
		List<CheckNo> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public CheckNo query(String username) {
		String hql = "from CheckNo cn where cn.username=?";
		@SuppressWarnings("unchecked")
		List<CheckNo> list = getHibernateTemplate().find(hql, username);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<CheckNo> queryList() {
		String hql = "from CheckNo cn";
		@SuppressWarnings("unchecked")
		List<CheckNo> list = getHibernateTemplate().find(hql);
		return list;
	}

}
