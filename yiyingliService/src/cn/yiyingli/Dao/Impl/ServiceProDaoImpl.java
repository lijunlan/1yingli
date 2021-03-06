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
import cn.yiyingli.Persistant.Teacher;
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
	public void updateAddLookNumber(long serviceProId, long number) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("update servicepro set servicepro.LOOKNUMBER=servicepro.LOOKNUMBER+"
				+ number + " where servicepro.SERVICEPRO_ID=" + serviceProId);
		query.executeUpdate();
	}

	@Override
	public void updateAndPlusNumber(ServicePro servicePro, boolean remove) {
		getHibernateTemplate().update(servicePro);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.SERVICEPRONUMBERFORUSER=(select count(*) from servicepro where servicepro.REMOVE=false and servicepro.STATE="
						+ ServiceProService.STATE_OK + " and servicepro.ONSHOW=true and servicepro.TEACHER_ID='"
						+ servicePro.getTeacher().getId() + "') where teacher.TEACHER_ID="
						+ servicePro.getTeacher().getId());
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
	public ServicePro queryDetail(long id) {
		String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.id=?";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public ServicePro queryByUser(long id) {
		String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
				+ " and sp.id=? and sp.onShow=" + true + " and sp.state=" + ServiceProService.STATE_OK;
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
	public List<ServicePro> queryListByState(final short state, final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.state="
						+ state + " ORDER BY sp.createTime DESC";
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
	public List<ServicePro> queryList(final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
						+ " ORDER BY sp.createTime DESC";
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

	@Override
	public List<ServicePro> queryListByIds(long[] idarray) {
		if (idarray.length <= 0)
			return new ArrayList<ServicePro>();
		String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
				+ " and sp.onShow=true and (sp.id=" + idarray[0];
		if (idarray.length > 1) {
			for (int i = 1; i < idarray.length; i++) {
				hql = hql + " or sp.id=" + idarray[i];
			}
		}
		hql = hql + ")";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryListByStateAndShow(final int page, final int pageSize, final short state,
			final boolean show) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false + " and sp.state="
						+ state + " and sp.onShow=" + show + " ORDER BY sp.createTime DESC";
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
	public List<ServicePro> queryListOtherByTeacher(final long serviceProId, final long teacherId, final int page,
			final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=false and sp.onShow=true and sp.state=1 and sp.id!="
						+ serviceProId + " and sp.teacher.id=" + teacherId + " ORDER BY sp.createTime DESC";
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
	public List<ServicePro> queryListByTeacherIdAndShow(final long teacherId, final short showKind, final int page,
			final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {

			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "";
				switch (showKind) {
				case SHOW_KIND_NONE:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " ORDER BY sp.createTime DESC";
					break;
				case SHOW_KIND_OFF:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " and sp.onShow=" + false
							+ " ORDER BY sp.createTime DESC";
					break;
				case SHOW_KIND_ON:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " and sp.onShow=" + true
							+ " ORDER BY sp.createTime DESC";
					break;
				default:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " ORDER BY sp.createTime DESC";
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
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " and state=" + state
							+ " ORDER BY sp.createTime DESC";
					break;
				case SHOW_KIND_OFF:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " and state=" + state + " and sp.onShow=" + false
							+ " ORDER BY sp.createTime DESC";
					break;
				case SHOW_KIND_ON:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " and state=" + state + " and sp.onShow=" + true
							+ " ORDER BY sp.createTime DESC";
					break;
				default:
					hql = "from ServicePro sp left join fetch sp.teacher where sp.remove=" + false
							+ " and sp.teacher.id=" + teacherId + " and state=" + state
							+ " ORDER BY sp.createTime DESC";
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
	public List<ServicePro> queryLikeListByUserId(final long userid, final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {

			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher left join fetch sp.userLikeServicePros uls where sp.remove=false and uls.user.id="
						+ userid + " ORDER BY uls.createTime DESC";

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
	public void removeUserLike(long serviceProId, long userId) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("delete from userlikeservicepro where userlikeservicepro.SERVICEPRO_ID='"
				+ serviceProId + "' and userlikeservicepro.USER_ID='" + userId + "'");
		query.executeUpdate();
		session.flush();
		query = session.createSQLQuery(
				"update servicepro set servicepro.LIKENO=(select count(*) from userlikeservicepro where userlikeservicepro.SERVICEPRO_ID='"
						+ serviceProId + "') where servicepro.SERVICEPRO_ID=" + serviceProId);
		query.executeUpdate();
		query = session.createSQLQuery(
				"update user set user.LIKESERVICEPRONUMBER=(select count(*) from userlikeservicepro where userlikeservicepro.USER_ID='"
						+ userId + "') where user.USER_ID=" + userId);
		query.executeUpdate();
	}

	@Override
	public Boolean queryCheckLikeUser(long serviceProId, long userId) {
		String hql = "from UserLikeServicePro uls where uls.user.id=?  and uls.servicePro.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userId, serviceProId);
		if (list.isEmpty())
			return false;
		else
			return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicePro> queryListByActivity(final String activityKey, final int page, final int pageSize) {
		List<ServicePro> list = new ArrayList<ServicePro>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ServicePro>>() {
			@Override
			public List<ServicePro> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ServicePro sp left join fetch sp.teacher left join fetch sp.contentAndPages spcap where spcap.pages.pagesKey='"
						+ activityKey + "' and sp.remove=false and sp.onShow=true ORDER BY spcap.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ServicePro> list = query.list();
				return list;
			}
		});
		return list;
	}

}
