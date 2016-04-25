package cn.yiyingli.Dao.Impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Persistant.Teacher;

public class TeacherDaoImpl extends HibernateDaoSupport implements TeacherDao {

	@Override
	public void save(Teacher teacher) {
		getHibernateTemplate().save(teacher);
	}

	@Override
	public Long saveAndReturnId(Teacher teacher) {
		getHibernateTemplate().save(teacher);
		return teacher.getId();
	}

	@Override
	public void remove(Teacher teacher) {
		getHibernateTemplate().delete(teacher);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Teacher t where t.id=?", id);
	}

	@Override
	public void removeAllTip(long teacherId) {
		Session session = getSession();
		Query query = session.createSQLQuery("delete from teacher_tip where TEACHER_ID='" + teacherId + "'");
		query.executeUpdate();
	}

	@Override
	public void removeUserLike(long teacherId, long userId) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("delete from userliketeacher where userliketeacher.TEACHER_ID='"
				+ teacherId + "' and userliketeacher.USER_ID='" + userId + "'");
		query.executeUpdate();
		session.flush();
		query = session.createSQLQuery(
				"update teacher set teacher.LIKENUMBER=(select count(*) from userliketeacher where userliketeacher.TEACHER_ID='"
						+ teacherId + "') where teacher.TEACHER_ID=" + teacherId);
		query.executeUpdate();
		query = session.createSQLQuery(
				"update user set user.LIKETEACHERNUMBER=(select count(*) from userliketeacher where userliketeacher.USER_ID='"
						+ userId + "') where user.USER_ID=" + userId);
		query.executeUpdate();
	}

	@Override
	public void merge(Teacher teacher) {
		getHibernateTemplate().merge(teacher);
	}

	@Override
	public void updateAddMile(long teacherId, float mile) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(
				"update teacher set teacher.MILE=teacher.MILE+" + mile + " where teacher.TEACHER_ID=" + teacherId);
		query.executeUpdate();
	}

	@Override
	public void updateAddSubMile(long teacherId, long subMile) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("update teacher set teacher.SUBMILE=teacher.SUBMILE+" + subMile
				+ " where teacher.TEACHER_ID=" + teacherId);
		query.executeUpdate();
	}

	@Override
	public void updateAddLookNumber(long teacherId, long number) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("update teacher set teacher.LOOKNUMBER=teacher.LOOKNUMBER+" + number
				+ " where teacher.TEACHER_ID=" + teacherId);
		query.executeUpdate();
	}

	@Override
	public void update(Teacher teacher) {
		getHibernateTemplate().update(teacher);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Boolean queryCheckMile(long teacherId, long subMile) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("select teacher.MILE>=(teacher.SUBMile+" + subMile
				+ ") from teacher where teacher.TEACHER_ID=" + teacherId + " for update");
		BigInteger r = (BigInteger) query.uniqueResult();
		if (r.intValue() != 1) {
			return false;
		}
		return true;
	}

	@Override
	public Teacher query(long id) {
		String hql = "from Teacher t where t.id=?  and t.onService=true";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryWithOutStatue(long id) {
		String hql = "from Teacher t where t.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryWithUser(long id) {
		String hql = "from Teacher t left join fetch t.servicePros left join fetch t.user where t.id=?  and t.onService=true";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Teacher> queryByIds(long[] ids) {
		if (ids.length <= 0)
			return new ArrayList<Teacher>();
		String hql = "from Teacher t left join fetch t.servicePros where t.id=" + ids[0];
		if (ids.length > 1) {
			for (int i = 1; i < ids.length; i++) {
				hql = hql + " or t.id=" + ids[i];
			}
		}
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public Boolean queryCheckLikeUser(long teacherId, long userId) {
		String hql = "from UserLikeTeacher ult where ult.user.id=?  and ult.teacher.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userId, teacherId);
		if (list.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public long querySumNoByInviterId(long inviterId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		long sum = (long) session.createQuery("select count(*) from Teacher t where t.inviter.id = " + inviterId)
				.uniqueResult();
		return sum;
	}

	public Teacher queryAll(long id) {
		String hql = "from Teacher t  where t.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryWithServiceProList(long id) {
		String hql = "from Teacher t left join fetch t.servicePros where t.id=?  and t.onService=true";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryForUser(long id) {
		String hql = "from Teacher t where t.id=? and t.onService=true";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryForTeacher(long id) {
		String hql = "from Teacher t where t.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryWithLikeUser(long teacherId) {
		String hql = "from Teacher t left join fetch t.likedUsers where t.id=?  and t.onService=true";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, teacherId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Teacher> queryByNameOrUsername(String word) {
		String hql = "from Teacher t where t.user.username=? or t.name like ?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, word, "%" + word + "%");
		return list;
	}

	@Override
	public Teacher queryByUserId(long userid) {
		String hql = "from Teacher t where t.user.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userid);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryByName(String name) {
		String hql = "from Teacher t where t.name=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, name);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryByUserIdWithServicePro(long userid) {
		String hql = "from Teacher t left join fetch t.servicePros where t.user.id=?  and t.onService=true";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userid);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryByInvitationCode(String invitationCode) {
		String hql = "from Teacher t where t.invitationCode = ?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql,invitationCode);
		if (list.isEmpty()) {
			return  null;
		} else {
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryLikeListByUserId(final long userid, final int page, final int pageSize,
			final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.servicePros left join fetch t.userLikeTeachers ult where ult.user.id="
						+ userid + " and t.onService=true ORDER BY ult.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.servicePros left join fetch t.userLikeTeachers ult where ult.user.id="
							+ userid + " and t.onService=true ORDER BY ult.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryListOnservice(final int page, final int pageSize, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.servicePros where t.onService=true ORDER BY t.id DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.servicePros where t.onService=true ORDER BY t.id DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryList(final int page, final int pageSize, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.servicePros ORDER BY t.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryListByKeyWord(final String keyword, final int page, final int pageSize,
			final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.servicePros where t.forSearch like '%" + keyword
						+ "%' and t.onService=true ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.servicePros where t.forSearch like '%" + keyword
							+ "%' and t.onService=true ORDER BY t.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryListByInviterId(final Long inviterId, final int page, final int pageSize) {
		List<Teacher> list = new ArrayList<>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {
			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t where t.inviter.id = " + inviterId +
						" and t.onService=true ORDER BY t.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page-1) * pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryListByActivity(final String activityKey, final int page, final int pageSize) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {
			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.contentAndPages tcap where tcap.pages.pagesKey='"
						+ activityKey + "'and t.onService=true ORDER BY tcap.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

}
