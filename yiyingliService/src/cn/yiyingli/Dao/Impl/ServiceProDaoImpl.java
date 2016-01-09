package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.ServiceProDao;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;

public class ServiceProDaoImpl extends HibernateDaoSupport implements ServiceProDao {

	@Override
	public void save(ServicePro servicePro) {
		getHibernateTemplate().save(servicePro);
	}

	@Override
	public Long saveAndReturnId(ServicePro servicePro) {
		getHibernateTemplate().save(servicePro);
		return servicePro.getId();
	}

	@Override
	public void saveAndPlusNumber(ServicePro servicePro, boolean byManager) {
		getHibernateTemplate().save(servicePro);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.SERVICEPRONUMBERFORTEACHER=(select count(*) from servicepro where servicepro.REMOVE=false and servicepro.TEACHER_ID='"
						+ servicePro.getTeacher().getId() + "') where teacher.TEACHER_ID="
						+ servicePro.getTeacher().getId());
		query.executeUpdate();
		if (byManager) {
			query = session.createSQLQuery(
					"update teacher set teacher.SERVICEPRONUMBERFORUSER=(select count(*) from servicepro where servicepro.REMOVE=false and servicepro.STATE="
							+ ServiceProService.STATE_OK + " and servicepro.TEACHER_ID='"
							+ servicePro.getTeacher().getId() + "') where teacher.TEACHER_ID="
							+ servicePro.getTeacher().getId());
			query.executeUpdate();
		}

	}

	@Override
	public void remove(ServicePro servicePro) {
		getHibernateTemplate().delete(servicePro);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from ServicePro sp where sp.id=?", id);
	}

	@Override
	public void update(ServicePro servicePro) {
		getHibernateTemplate().update(servicePro);
	}

	@Override
	public void updateAndPlusNumber(ServicePro servicePro, boolean remove) {
		getHibernateTemplate().update(servicePro);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.SERVICEPRONUMBERFORUSER=(select count(*) from servicepro where servicepro.REMOVE=false and servicepro.STATE="
						+ ServiceProService.STATE_OK + " and servicepro.TEACHER_ID='" + servicePro.getTeacher().getId()
						+ "') where teacher.TEACHER_ID=" + servicePro.getTeacher().getId());
		query.executeUpdate();
		if (remove) {
			query = session.createSQLQuery(
					"update teacher set teacher.SERVICEPRONUMBERFORTEACHER=(select count(*) from servicepro where servicepro.REMOVE=false and servicepro.TEACHER_ID='"
							+ servicePro.getTeacher().getId() + "') where teacher.TEACHER_ID="
							+ servicePro.getTeacher().getId());
			query.executeUpdate();
		}
	}

	@Override
	public ServicePro querySimple(long id) {
		String hql = "from ServicePro sp where sp.remove=" + false + " and sp.id=?";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public ServicePro queryByUser(long id) {
		String hql = "from ServicePro sp where sp.remove=" + false + " and sp.id=" + id + " and sp.onShow=" + true
				+ " and sp.state=" + ServiceProService.STATE_OK;
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId) {
		String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
				+ " and sp.id=? and sp.teacher.id=?";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql, serviceId, teacherId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryList(final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " ORDER BY sp.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ServicePro> list = query.list();
				return list;
			}
		});
		return list;
	}

	@Override
	public List<ServicePro> queryList(long[] ids, long teacherId) {
		if (ids.length <= 0)
			return new ArrayList<ServicePro>();
		String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.teacher.id="
				+ teacherId + " and (sp.id=" + ids[0];
		if (ids.length > 1) {
			for (int i = 1; i < ids.length; i++) {
				hql = hql + " or sp.id=" + ids[i];
			}
		}
		hql = hql + ")";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryListByTeacherIdAndShow(final long teacherId, final short showKind, final int page,
			final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {

			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "";
				switch (showKind) {
				case SHOW_KIND_NONE:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " ORDER BY sp.rankNo ASC";
					break;
				case SHOW_KIND_OFF:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " and sp.onShow=" + false + " ORDER BY sp.rankNo ASC";
					break;
				case SHOW_KIND_ON:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " and sp.onShow=" + true + " ORDER BY sp.rankNo ASC";
					break;
				default:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " ORDER BY sp.rankNo ASC";
					break;
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ServicePro> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryListByTeacherIdAndShowAndState(final long teacherId, final short showKind,
			final short state, final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {

			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "";
				switch (showKind) {
				case SHOW_KIND_NONE:
					hql = "from ServicePro sp where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " and state=" + state + " ORDER BY sp.rankNo ASC";
					break;
				case SHOW_KIND_OFF:
					hql = "from ServicePro sp where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " and state=" + state + " and sp.onShow=" + false + " ORDER BY sp.rankNo ASC";
					break;
				case SHOW_KIND_ON:
					hql = "from ServicePro sp where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " and state=" + state + " and sp.onShow=" + true + " ORDER BY sp.rankNo ASC";
					break;
				default:
					hql = "from ServicePro sp where sp.remove=" + false + " and sp.teacher.id=" + teacherId
							+ " and state=" + state + " ORDER BY sp.rankNo ASC";
					break;
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ServicePro> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryListByHomePage(final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.onShow=true and sp.homeWeight!=0 ORDER BY sp.homeWeight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(pageSize);
				List<ServicePro> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryListBySale(final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.onShow=true and sp.saleWeight!=0 ORDER BY sp.saleWeight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ServicePro> list = query.list();
				return list;
			}
		});
		return list;
	}

	@Override
	public long queryListBySaleNo() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		long sum = (long) session
				.createQuery("select count(*) from ServicePro sp where sp.onShow=true and sp.saleWeight!=0")
				.uniqueResult();
		return sum;
	}
}
