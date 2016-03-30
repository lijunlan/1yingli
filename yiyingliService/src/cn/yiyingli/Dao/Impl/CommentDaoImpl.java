package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.CommentDao;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;

public class CommentDaoImpl extends HibernateDaoSupport implements CommentDao {

	@Override
	public void save(Comment comment) {
		getHibernateTemplate().save(comment);

	}

	@Override
	public Long saveAndReturnId(Comment comment) {
		getHibernateTemplate().save(comment);
		return comment.getId();
	}

	@Override
	public void saveWithTeacherAndUser(Comment comment, Teacher teacher, User user, short kind) {
		getHibernateTemplate().save(comment);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.COMMENTNUMBER=(select count(*) from comment where comment.TEACHER_ID='"
						+ teacher.getId() + "' and kind=" + kind + ") where teacher.TEACHER_ID=" + teacher.getId());
		query.executeUpdate();
		query = session.createSQLQuery(
				"update user set user.SENDCOMMENTNUMBER=(select count(*) from comment where comment.USER_ID='"
						+ user.getId() + "' and kind=" + kind + ") where user.USER_ID=" + user.getId());
		query.executeUpdate();
		if (comment.getServicePro() != null) {
			query = session.createSQLQuery(
					"update servicepro set servicepro.COMMENTNO=(select count(*) from comment where comment.SERVICEPRO_ID='"
							+ comment.getServicePro().getId() + "' and kind=" + kind
							+ ") where servicepro.SERVICEPRO_ID=" + comment.getServicePro().getId());
			query.executeUpdate();
		}
	}

	@Override
	public void saveWithUser(Comment comment, User user, short kind) {
		getHibernateTemplate().save(comment);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update user set user.RECEIVECOMMENTNUMBER=(select count(*) from comment where comment.USER_ID='"
						+ user.getId() + "' and kind=" + kind + ") where user.USER_ID=" + user.getId());
		query.executeUpdate();
	}

	@Override
	public void remove(Comment comment) {
		getHibernateTemplate().delete(comment);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Comment c where c.id=?", id);
	}

	@Override
	public void update(Comment comment) {
		getHibernateTemplate().update(comment);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Comment query(int id, boolean lazy) {
		String hql = "from Comment c where c.id=?";
		if (lazy) {
			hql = "from Comment c left join fetch c.teacher left join fetch c.user where c.id=?";
		}
		@SuppressWarnings("unchecked")
		List<Comment> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Comment> queryDoubleByOrderIdAndTeacherId(long orderId, long teacherId, boolean lazy) {
		String hql = "from Comment c where c.ownOrder.id=? and c.teacher.id=?";
		if (lazy) {
			hql = "from Comment c left join fetch c.teacher left join fetch c.user left join fetch c.ownOrder where c.id=?";
		}
		@SuppressWarnings("unchecked")
		List<Comment> list = getHibernateTemplate().find(hql, orderId, teacherId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> queryListByUserId(final long userId, final int page, final int pageSize, final short kind,
			final boolean lazy) {
		List<Comment> list = new ArrayList<Comment>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Comment>>() {

			@Override
			public List<Comment> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comment c where c.user.id=" + userId + " and c.kind=" + kind
						+ " ORDER BY c.createTime DESC";
				if (lazy) {
					hql = "from Comment c left join fetch c.teacher left join fetch c.user where c.user.id=" + userId
							+ " and c.kind=" + kind + " ORDER BY c.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Comment> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> queryListByUserIdAndScore(final long userId, final short score, final int page,
			final int pageSize, final short kind, final boolean lazy) {
		List<Comment> list = new ArrayList<Comment>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Comment>>() {
			@Override
			public List<Comment> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comment c where c.user.id=" + userId + " and c.score=" + score + " and c.kind="
						+ kind + " ORDER BY c.createTime DESC";
				if (lazy) {
					hql = "from Comment c left join fetch c.teacher left join fetch c.user where c.user.id=" + userId
							+ " and c.score=" + score + " and c.kind=" + kind + " ORDER BY c.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Comment> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> queryListByTeacherId(final long teacherId, final int page, final int pageSize,
			final short kind, final boolean lazy) {
		List<Comment> list = new ArrayList<Comment>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Comment>>() {

			@Override
			public List<Comment> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comment c where c.teacher.id=" + teacherId + " and c.kind=" + kind
						+ " ORDER BY c.createTime DESC";
				if (lazy) {
					hql = "from Comment c left join fetch c.teacher left join fetch c.user left join fetch c.servicePro where c.teacher.id="
							+ teacherId + " and c.kind=" + kind + " ORDER BY c.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Comment> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> queryListByTeacherIdAndScore(final long teacherId, final short score, final int page,
			final int pageSize, final short kind, final boolean lazy) {
		List<Comment> list = new ArrayList<Comment>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Comment>>() {
			@Override
			public List<Comment> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comment c where c.teacher.id=" + teacherId + " and c.score=" + score + " and c.kind="
						+ kind + " ORDER BY c.createTime DESC";
				if (lazy) {
					hql = "from Comment c left join fetch c.teacher left join fetch c.user where c.teacher.id="
							+ teacherId + " and c.score=" + score + " and c.kind=" + kind
							+ " ORDER BY c.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Comment> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> queryListByServiceProId(final long serviceProId, final int page, final int pageSize,
			final short kind) {
		List<Comment> list = new ArrayList<Comment>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Comment>>() {
			@Override
			public List<Comment> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comment c left join fetch c.user where c.servicePro.id=" + serviceProId + " and c.kind=" + kind
						+ " ORDER BY c.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Comment> list = query.list();
				return list;
			}
		});
		return list;
	}

	@Override
	public long querySumNoByUserId(long userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Comment c where c.user.id=" + userId)
				.uniqueResult();
		ts.commit();
		return sum;
	}

	@Override
	public long querySumNoByTeacherId(long teacherId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Comment c where c.teacher.id=" + teacherId)
				.uniqueResult();
		ts.commit();
		return sum;
	}

}
