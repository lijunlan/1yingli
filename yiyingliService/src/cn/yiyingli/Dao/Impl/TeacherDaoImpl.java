package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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
	public void update(Teacher teacher) {
		getHibernateTemplate().update(teacher);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Teacher query(long id, boolean lazy) {
		String hql = "from Teacher t left join fetch t.tService where t.id=?  and t.onService=true";
		if (lazy) {
			hql = "from Teacher t left join fetch t.tips " + " left join fetch t.comments "
					+ " left join fetch t.tService where t.id=?  and t.onService=true";
		}
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
		String hql = "from Teacher t left join fetch t.tService where t.id=" + ids[0];
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
	public Teacher queryWithTips(long id, boolean lazy) {
		String hql = "from Teacher t left join fetch t.tService left join fetch t.tips where t.id=?  and t.onService=true";
		if (lazy) {
			hql = "from Teacher t left join fetch t.tService left join fetch t.tips where t.id=?  and t.onService=true";
		}
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryWithLikeUser(long teacherId, boolean lazy) {
		String hql = "from Teacher t left join fetch t.likedUsers where t.id=?  and t.onService=true";
		if (lazy) {
			hql = "from Teacher t left join fetch t.likedUsers where t.id=?  and t.onService=true";
		}
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, teacherId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryByUserId(long userid, boolean lazy) {
		String hql = "from Teacher t where t.user.id=?  and t.onService=true";
		if (lazy) {
			hql = "from Teacher t where t.user.id=?  and t.onService=true";
		}
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userid);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Teacher queryByUserIdWithTService(long userid, boolean lazy) {
		String hql = "from Teacher t left join fetch t.tService where t.user.id=?  and t.onService=true";
		if (lazy) {
			hql = "from Teacher t left join fetch t.tService where t.user.id=?  and t.onService=true";
		}
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userid);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryByTipOrderByShow(final int size, final long tipMark, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")="
						+ tipMark + " and t.onService=true ORDER BY t.showWeight" + tipMark + " DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")=" + tipMark
							+ " and t.onService=true ORDER BY t.showWeight" + tipMark + " DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(size);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryLikeListByUserId(final long userid, final int page, final int pageSize,
			final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService left join fetch t.userLikeTeachers ult where ult.user.id="
						+ userid + " and t.onService=true ORDER BY ult.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService left join fetch t.userLikeTeachers ult where ult.user.id="
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
	public List<Teacher> queryList(final int page, final int pageSize, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService  ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService  ORDER BY t.createTime DESC";
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
				String hql = "from Teacher t left join fetch t.tService where t.forSearch like '%" + keyword
						+ "%' and t.onService=true ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where t.forSearch like '%" + keyword
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
	public List<Teacher> queryListByTipOrderByLikeNo(final int page, final int pageSize, final long tipMark,
			final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")="
						+ tipMark + " and t.onService=true ORDER BY t.likeNumber DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")=" + tipMark
							+ " and t.onService=true ORDER BY t.likeNumber DESC";
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
	public List<Teacher> queryListByTip(final int page, final int pageSize, final long tipMark, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")="
						+ tipMark + " and t.onService=true ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")=" + tipMark
							+ " and t.onService=true ORDER BY t.createTime DESC";
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
	public List<Teacher> queryListByLevel(final short level, final int page, final int pageSize, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {
			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where t.level=" + level
						+ " and t.onService=true ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where t.level=" + level
							+ " and t.onService=true ORDER BY t.createTime DESC";
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
	public List<Teacher> queryListByTipAndLevel(final int page, final int pageSize, final long tipMark,
			final short level, final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {
			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where t.level=" + level
						+ " and bitand(t.tipMark," + tipMark + ")=" + tipMark
						+ " and t.onService=true ORDER BY t.createTime DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where t.level=" + level + " and bitand(t.tipMark,"
							+ tipMark + ")=" + tipMark + " and t.onService=true ORDER BY t.createTime DESC";
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
	public List<Teacher> queryListByTipAndLastId(final long lastId, final int size, final long tipMark,
			final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")="
						+ tipMark + " and t.id<" + lastId + " and t.onService=true ORDER BY t.id DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")=" + tipMark
							+ " and t.id<" + lastId + " and t.onService=true ORDER BY t.id DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(size);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryListByTipAndFirstId(final long firstId, final int size, final long tipMark,
			final boolean lazy) {
		List<Teacher> list = new ArrayList<Teacher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {

			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")="
						+ tipMark + " and t.id>" + firstId + " and t.onService=true ORDER BY t.id DESC";
				if (lazy) {
					hql = "from Teacher t left join fetch t.tService where bitand(t.tipMark," + tipMark + ")=" + tipMark
							+ " and t.id>" + firstId + " and t.onService=true ORDER BY t.id DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(size);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

}
