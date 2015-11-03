package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.DistributorMarkDao;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.DistributorMark;

public class DistributorMarkDaoImpl extends HibernateDaoSupport implements DistributorMarkDao {

	@Override
	public void save(DistributorMark distributorMark) {
		getHibernateTemplate().save(distributorMark);
	}

	@Override
	public void remove(DistributorMark distributorMark) {
		getHibernateTemplate().delete(distributorMark);
	}

	@Override
	public void remove(String UUID) {
		getHibernateTemplate().bulkUpdate("delete from DistributorMark dm where dm.uuid=?", UUID);
	}

	@Override
	public void update(DistributorMark distributorMark) {
		getHibernateTemplate().update(distributorMark);
	}

	@Override
	public void update(String distributorId, String UUID) {
		getHibernateTemplate().bulkUpdate(
				"update DistributorMark dm set dm.uuid='" + UUID + "' where dm.distributor.id=" + distributorId);
	}

	@Override
	public DistributorMark queryUUID(long distributorId) {
		String hql = "from DistributorMark dm  where dm.distributor.id=?";
		@SuppressWarnings("unchecked")
		List<DistributorMark> list = getHibernateTemplate().find(hql, distributorId);
		if (list.isEmpty()) {
			return null;
		} else
			return list.get(0);
	}

	@Override
	public DistributorMark query(String UUID) {
		String hql = "from DistributorMark dm where dm.uuid=?";
		@SuppressWarnings("unchecked")
		List<DistributorMark> list = getHibernateTemplate().find(hql, UUID);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Distributor queryDistributor(String UUID) {
		String hql = "from DistributorMark dm left join fetch dm.distributor where dm.uuid=?";
		@SuppressWarnings("unchecked")
		List<DistributorMark> list = getHibernateTemplate().find(hql, UUID);
		if (list.isEmpty())
			return null;
		else
			return list.get(0).getDistributor();
	}

}
