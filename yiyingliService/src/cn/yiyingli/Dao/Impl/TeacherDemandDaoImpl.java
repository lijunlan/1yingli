package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.TeacherDemandDao;
import cn.yiyingli.Persistant.TeacherDemand;

public class TeacherDemandDaoImpl extends HibernateDaoSupport implements TeacherDemandDao {

	@Override
	public void save(TeacherDemand teacherDemand) {
		getHibernateTemplate().save(teacherDemand);
	}

	@Override
	public void remove(TeacherDemand teacherDemand) {
		getHibernateTemplate().delete(teacherDemand);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from TeacherDemand td where td.id=?", id);
	}

	@Override
	public void update(TeacherDemand teacherDemand) {
		getHibernateTemplate().update(teacherDemand);
	}

	@Override
	public TeacherDemand query(long id) {
		String hql = "from TeacherDemand td where td.id=?";
		@SuppressWarnings("unchecked")
		List<TeacherDemand> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<TeacherDemand> queryList() {
		String hql = "from TeacherDemand td";
		@SuppressWarnings("unchecked")
		List<TeacherDemand> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherDemand> queryListByTime(final int page, final int pageSize) {
		List<TeacherDemand> list = new ArrayList<TeacherDemand>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<TeacherDemand>>() {
			@Override
			public List<TeacherDemand> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from TeacherDemand td ORDER BY td.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<TeacherDemand> list = query.list();
				return list;
			}
		});
		return list;
	}

}
