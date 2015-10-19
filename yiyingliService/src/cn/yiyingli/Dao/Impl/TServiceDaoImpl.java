package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.TServiceDao;
import cn.yiyingli.Persistant.TService;

public class TServiceDaoImpl extends HibernateDaoSupport implements TServiceDao {

	@Override
	public void save(TService tService) {
		getHibernateTemplate().save(tService);
	}

	@Override
	public Long saveAndReturnId(TService tService) {
		getHibernateTemplate().save(tService);
		return tService.getId();
	}

	@Override
	public void remove(TService tService) {
		getHibernateTemplate().delete(tService);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate(
				"delete from TService ts where ts.id=?", id);
	}

	@Override
	public void update(TService tService) {
		getHibernateTemplate().update(tService);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public TService query(long id, boolean lazy) {
		String hql = "from TServicet ts where ts.id=?";
		if (lazy) {
			hql = "from TService ts right join fetch ts.freeTimes right join fetch ts.orders"
					+ " right join fetch ts.teacherStore where ts.id=?";
		}
		@SuppressWarnings("unchecked")
		List<TService> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public TService queryByTeacherStoreId(long teacherStoreId, boolean lazy) {
		String hql = "from TServicet ts where ts.teacherStore.id=?";
		if (lazy) {
			hql = "from TService ts right join fetch ts.freeTimes right join fetch ts.orders where ts.teacherStore.id=?";
		}
		@SuppressWarnings("unchecked")
		List<TService> list = getHibernateTemplate().find(hql, teacherStoreId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

}
