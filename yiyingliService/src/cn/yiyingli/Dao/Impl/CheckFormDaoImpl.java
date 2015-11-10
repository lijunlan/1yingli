package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.CheckFormDao;
import cn.yiyingli.Persistant.CheckForm;

public class CheckFormDaoImpl extends HibernateDaoSupport implements CheckFormDao {

	@Override
	public void save(CheckForm checkForm) {
		getHibernateTemplate().save(checkForm);
	}

	@Override
	public Long saveAndReturnId(CheckForm checkForm) {
		getHibernateTemplate().save(checkForm);
		return checkForm.getId();
	}

	@Override
	public void remove(CheckForm checkForm) {
		getHibernateTemplate().delete(checkForm);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from CheckForm cf where cf.id=?", id);
	}

	@Override
	public void update(CheckForm checkForm) {
		getHibernateTemplate().update(checkForm);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public CheckForm query(long id) {
		String hql = "from CheckForm cf left join fetch cf.teacher where cf.id=?";
		@SuppressWarnings("unchecked")
		List<CheckForm> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public CheckForm query(String teacherId) {
		String hql = "from CheckForm cf left join fetch cf.teacher where cf.teacher.id=?";
		@SuppressWarnings("unchecked")
		List<CheckForm> list = getHibernateTemplate().find(hql, teacherId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<CheckForm> queryList() {
		String hql = "from CheckForm cf left join fetch cf.teacher ORDER BY cf.createTime DESC";
		@SuppressWarnings("unchecked")
		List<CheckForm> list = getHibernateTemplate().find(hql);
		return list;
	}

}
