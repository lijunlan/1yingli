package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.ManagerMarkDao;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.ManagerMark;

public class ManagerMarkDaoImpl extends HibernateDaoSupport implements ManagerMarkDao {

	@Override
	public void save(ManagerMark userMark) {
		getHibernateTemplate().save(userMark);
	}

	@Override
	public void remove(ManagerMark userMark) {
		getHibernateTemplate().delete(userMark);
	}

	@Override
	public void remove(String UUID) {
		getHibernateTemplate().bulkUpdate("delete from ManagerMark mm where mm.uuid=?", UUID);
	}

	@Override
	public void update(ManagerMark userMark) {
		getHibernateTemplate().update(userMark);
	}

	@Override
	public void update(String managerId, String UUID) {
		getHibernateTemplate()
				.bulkUpdate("update ManagerMark mm set mm.uuid='" + UUID + "' where mm.manager.id=" + managerId);
	}

	@Override
	public ManagerMark queryUUID(long managerId) {
		String hql = "from ManagerMark mm  where mm.manager.id=?";
		@SuppressWarnings("unchecked")
		List<ManagerMark> list = getHibernateTemplate().find(hql, managerId);
		if (list.isEmpty()) {
			return null;
		} else
			return list.get(0);
	}

	@Override
	public ManagerMark query(String UUID) {
		String hql = "from ManagerMark mm where mm.uuid=?";
		@SuppressWarnings("unchecked")
		List<ManagerMark> list = getHibernateTemplate().find(hql, UUID);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Manager queryManager(String UUID) {
		String hql = "from ManagerMark mm left join fetch mm.manager where mm.uuid=?";
		@SuppressWarnings("unchecked")
		List<ManagerMark> list = getHibernateTemplate().find(hql, UUID);
		if (list.isEmpty())
			return null;
		else
			return list.get(0).getManager();
	}

}
