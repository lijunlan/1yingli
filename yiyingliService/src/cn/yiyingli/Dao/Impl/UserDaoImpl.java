package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserLikePassage;
import cn.yiyingli.Persistant.UserLikeTeacher;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Override
	public void save(User user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public void saveAndCount(User user, Distributor distributor) {
		getHibernateTemplate().save(user);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update distributor set distributor.REGISTERNUMBER=(select count(*) from user where user.DISTRIBUTOR_ID='"
						+ distributor.getId() + "') where distributor.DISTRIBUTOR_ID=" + distributor.getId());
		query.executeUpdate();
	}

	@Override
	public Long saveAndReturnId(User user) {
		getHibernateTemplate().save(user);
		return user.getId();
	}

	@Override
	public void remove(User user) {
		getHibernateTemplate().delete(user);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from User u where u.id=?", id);
	}

	@Override
	public void merge(User user) {
		getHibernateTemplate().merge(user);
	}

	@Override
	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	@Override
	public void updateUsername(User user) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(
				"update user set user.USERNAME=" + user.getUsername() + " where user.USER_ID=" + user.getId());
		query.executeUpdate();
		query = session.createSQLQuery(
				"update teacher set teacher.USERNAME=" + user.getUsername() + " where teacher.USER_ID=" + user.getId());
		query.executeUpdate();
	}

	@Override
	public void updateLikeTeacher(UserLikeTeacher userLikeTeacher) {
		getHibernateTemplate().save(userLikeTeacher);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update user set user.LIKETEACHERNUMBER=(select count(*) from userliketeacher where userliketeacher.USER_ID='"
						+ userLikeTeacher.getUser().getId() + "') where user.USER_ID="
						+ userLikeTeacher.getUser().getId());
		query.executeUpdate();
		query = session.createSQLQuery(
				"update teacher set teacher.LIKENUMBER=(select count(*) from userliketeacher where userliketeacher.TEACHER_ID='"
						+ userLikeTeacher.getTeacher().getId() + "') where teacher.TEACHER_ID="
						+ userLikeTeacher.getTeacher().getId());
		query.executeUpdate();
	}

	@Override
	public void updateLikePassage(UserLikePassage userLikePassage) {
		getHibernateTemplate().save(userLikePassage);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update passage set passage.LIKENUMBER=(select count(*) from userlikepassage where userlikepassage.PASSAGE_ID='"
						+ userLikePassage.getPassage().getId() + "') where passage.PASSAGE_ID="
						+ userLikePassage.getPassage().getId());
		query.executeUpdate();
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public User query(long id, boolean lazy) {
		String hql = "from User u where u.id=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.id=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryByTeacherId(long teacherId) {
		String hql = "from User u where u.teacher.id=?";
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, teacherId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryByNo(String no, boolean lazy) {
		String hql = "from User u where u.username=? or u.phone=? or u.email=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.username=? or u.phone=? or u.email=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, no, no, no);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryWithTeacher(long id, boolean lazy) {
		String hql = "from User u left join fetch u.teacher where u.id=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.id=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User query(String username, boolean lazy) {
		String hql = "from User u where u.username=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.username=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, username);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryWithWeibo(String weiboNo, boolean lazy) {
		String hql = "from User u where u.weiboNo=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.weiboNo=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, weiboNo);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryWithWeixin(String weixinNo, boolean lazy) {
		String hql = "from User u where u.wechatNo=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.wechatNo=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, weixinNo);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryWithWeixinPlatform(String weixinNo) {
		String hql = "from User u where u.wechatPlatformNo=?";
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, weixinNo);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryWithTeacher(String username, boolean lazy) {
		String hql = "from User u left join fetch u.teacher where u.username=?";
		if (lazy) {
			hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
					+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
					+ " left join fetch u.givecomments where u.username=?";
		}
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().find(hql, username);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryList(final int page, final int pageSize, final boolean lazy) {
		List<User> list = new ArrayList<User>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<User>>() {
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from User u ORDER BY u.createTime DESC";
				if (lazy) {
					hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
							+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
							+ " left join fetch u.givecomments ORDER BY u.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<User> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryListByForbid(final int page, final int pageSize, final boolean forbid, final boolean lazy) {
		List<User> list = new ArrayList<User>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<User>>() {
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from User u where forbid=" + forbid + " ORDER BY u.phone DESC";
				if (lazy) {
					hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
							+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
							+ " left join fetch u.givecomments where forbid=" + forbid + " ORDER BY u.phone DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<User> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryListByValidate(final int page, final int pageSize, final boolean validate,
			final boolean lazy) {
		List<User> list = new ArrayList<User>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<User>>() {
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from User u where validate=" + validate + " ORDER BY u.phone DESC";
				if (lazy) {
					hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
							+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
							+ " left join fetch u.givecomments where validate=" + validate + " ORDER BY u.phone DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<User> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryListByLevel(final int page, final int pageSize, final short level, final boolean lazy) {
		List<User> list = new ArrayList<User>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<User>>() {
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from User u where level=" + level + " ORDER BY u.phone DESC";
				if (lazy) {
					hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
							+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
							+ " left join fetch u.givecomments where level=" + level + " ORDER BY u.phone DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<User> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryListByTeacher(final int page, final int pageSize, final short teacherState,
			final boolean lazy) {
		List<User> list = new ArrayList<User>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<User>>() {
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from User u where teacherState=" + teacherState + " ORDER BY u.phone DESC";
				if (lazy) {
					hql = "from User u left join fetch u.orders left join fetch u.linkinInfos "
							+ "left join fetch u.teacher left join fetch u.cvs left join fetch u.ownSiteDiscounts"
							+ " left join fetch u.givecomments where teacherState=" + teacherState
							+ " ORDER BY u.phone DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<User> list = query.list();
				return list;
			}
		});
		return list;
	}

}
