package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.UserMarkDao;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserMark;

public class UserMarkDaoImpl extends HibernateDaoSupport implements UserMarkDao {

	@Override
	public void save(UserMark userMark) {
		getHibernateTemplate().save(userMark);
	}

	@Override
	public void update(UserMark userMark) {
		getHibernateTemplate().update(userMark);
	}

	@Override
	public void update(String userId, String UUID) {
		getHibernateTemplate().bulkUpdate("update UserMark um set um.uuid='" + UUID + "' where um.user.id=" + userId);
	}

	@Override
	public void remove(UserMark userMark) {
		getHibernateTemplate().delete(userMark);
	}

	@Override
	public void remove(String UUID) {
		getHibernateTemplate().bulkUpdate("delete from UserMark u where u.uuid=?", UUID);
	}

	public UserMark queryUUID(long userId) {
		String hql = "from UserMark um  where um.user.id=?";
		@SuppressWarnings("unchecked")
		List<UserMark> list = getHibernateTemplate().find(hql, userId);
		if (list.isEmpty()) {
			return null;
		} else
			return list.get(0);
	}

	@Override
	public UserMark query(String UUID) {
		String hql = "from UserMark u where u.uuid=?";
		@SuppressWarnings("unchecked")
		List<UserMark> list = getHibernateTemplate().find(hql, UUID);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public User queryUser(String UUID) {
		String hql = "from UserMark um left join fetch um.user left join fetch um.user.distributor where um.uuid=?";
		@SuppressWarnings("unchecked")
		List<UserMark> list = getHibernateTemplate().find(hql, UUID);
		if (list.isEmpty())
			return null;
		else
			return list.get(0).getUser();
	}

}
