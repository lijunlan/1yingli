package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.FreeTimeDao;
import cn.yiyingli.Persistant.FreeTime;

public class FreeTimeDaoImpl extends HibernateDaoSupport implements FreeTimeDao {

	@Override
	public void save(FreeTime freeTime) {
		getHibernateTemplate().save(freeTime);
	}

	@Override
	public Long saveAndReturnId(FreeTime freeTime) {
		getHibernateTemplate().save(freeTime);
		return freeTime.getId();
	}

	@Override
	public void remove(FreeTime freeTime) {
		getHibernateTemplate().delete(freeTime);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate(
				"delete from FreeTime ft where ft.id=?", id);
	}

	@Override
	public void removeAll(long teacherId) {
		getHibernateTemplate().bulkUpdate(
				"delete from FreeTime ft where ft.teacher.id=?", teacherId);
	}

	@Override
	public void update(FreeTime freeTime) {
		getHibernateTemplate().update(freeTime);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public FreeTime query(long id, boolean lazy) {
		String hql = "from FreeTime ft where ft.id=?";
		if (lazy) {
			hql = "from FreeTime ft left join fetch ft.teacher left join fetch ft.tServices where ft.id=?";
		}
		@SuppressWarnings("unchecked")
		List<FreeTime> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FreeTime> queryListByTeacherId(long teacherId, boolean lazy) {
		String hql = "from FreeTime ft where ft.teacher.id=?";
		if (lazy) {
			hql = "from FreeTime ft left join fetch ft.tServices where ft.teacher.id=?";
		}
		return getHibernateTemplate().find(hql, teacherId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FreeTime> queryListByTServiceId(long tServiceId, boolean lazy) {
		String hql = "from FreeTime ft where ft.tServices.id=?";
		if (lazy) {
			hql = "from FreeTime ft left join fetch ft.teacher where ft.tServices.id=?";
		}
		return getHibernateTemplate().find(hql, tServiceId);
	}

}
