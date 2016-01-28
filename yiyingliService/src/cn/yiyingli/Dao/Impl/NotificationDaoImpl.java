package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.NotificationDao;
import cn.yiyingli.Persistant.Notification;

public class NotificationDaoImpl extends HibernateDaoSupport implements NotificationDao {

	@Override
	public void save(Notification notification) {
		getHibernateTemplate().save(notification);
	}

	@Override
	public Long saveAndReturnId(Notification notification) {
		getHibernateTemplate().save(notification);
		return notification.getId();
	}

	@Override
	public void remove(Notification notification) {
		getHibernateTemplate().delete(notification);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Notification n where n.id=?", id);
	}

	@Override
	public void update(Notification notification) {
		getHibernateTemplate().update(notification);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Notification query(long id, boolean lazy) {
		String hql = "from Notification n where n.id=?";
		if (lazy) {
			hql = "from Notification n left join fetch n.toUser where n.id=?";
		}
		@SuppressWarnings("unchecked")
		List<Notification> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public long querySumNo() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Notification n").uniqueResult();
		return sum;
	}

	@Override
	public long querySumNo(long userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Notification n where n.toUser.id=" + userId)
				.uniqueResult();
		return sum;
	}

	@Override
	public long queryUnreadSumNo(long userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		long sum = (long) session
				.createQuery("select count(*) from Notification n where n.toUser.id=" + userId + " and n.read=false")
				.uniqueResult();
		return sum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> queryList(final int page, final int pageSize, final boolean lazy) {
		List<Notification> list = new ArrayList<Notification>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Notification>>() {

			@Override
			public List<Notification> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Notification n ORDER BY n.createTime DESC";
				if (lazy) {
					hql = "from Notification n left join fetch n.toUser ORDER BY n.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Notification> list = query.list();
				return list;
			}
		});
		return list;
	}

	@Override
	public void updateReadByIds(long[] ids) {
		if (ids.length <= 0) {
			return;
		}
		String hql = "update Notification n set n.read=true where n.id=" + ids[0];
		if (ids.length > 1) {
			for (int i = 1; i < ids.length; i++) {
				hql = hql + " or n.id=" + ids[i];
			}
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.createQuery(hql).executeUpdate();
	}

	@Override
	public void updateReadAll(long userId) {
		String hql = "update Notification n set n.read=true where n.toUser.id=" + userId;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> queryListByUserId(final long userId, final int page, final int pageSize,
			final boolean lazy) {
		List<Notification> list = new ArrayList<Notification>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Notification>>() {

			@Override
			public List<Notification> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Notification n where n.toUser.id=" + userId + " ORDER BY n.createTime DESC";
				if (lazy) {
					hql = "from Notification n left join fetch n.toUser where n.toUser.id=" + userId
							+ " ORDER BY n.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Notification> list = query.list();
				return list;
			}
		});
		return list;
	}

}
