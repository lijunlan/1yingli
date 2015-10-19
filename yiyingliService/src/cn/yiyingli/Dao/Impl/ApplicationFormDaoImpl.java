package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.ApplicationFormDao;
import cn.yiyingli.Persistant.ApplicationForm;

public class ApplicationFormDaoImpl extends HibernateDaoSupport implements ApplicationFormDao {

	@Override
	public void save(ApplicationForm checkApplication) {
		getHibernateTemplate().save(checkApplication);
	}

	@Override
	public Long saveAndReturnId(ApplicationForm checkApplication) {
		getHibernateTemplate().save(checkApplication);
		return checkApplication.getId();
	}

	@Override
	public void remove(ApplicationForm checkApplication) {
		getHibernateTemplate().delete(checkApplication);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from ApplicationForm af where af.id=?", id);
	}

	@Override
	public void update(ApplicationForm checkApplication) {
		getHibernateTemplate().update(checkApplication);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public ApplicationForm query(long id) {
		String hql = "from ApplicationForm af left join fetch af.user left join fetch af.teacher left join fetch af.teacher.tService where af.id=?";
		@SuppressWarnings("unchecked")
		List<ApplicationForm> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public ApplicationForm query(String userId) {
		String hql = "from ApplicationForm af left join fetch af.user left join fetch af.teacher left join fetch af.teacher.tService where af.user.id=?";
		@SuppressWarnings("unchecked")
		List<ApplicationForm> list = getHibernateTemplate().find(hql, userId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<ApplicationForm> queryList() {
		String hql = "from ApplicationForm af left join fetch af.user left join fetch af.teacher left join fetch af.teacher.tService ORDER BY af.createTime DESC";
		@SuppressWarnings("unchecked")
		List<ApplicationForm> list = getHibernateTemplate().find(hql);
		return list;
	}

}
