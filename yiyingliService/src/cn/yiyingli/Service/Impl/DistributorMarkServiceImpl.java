package cn.yiyingli.Service.Impl;

import cn.yiyingli.Dao.DistributorDao;
import cn.yiyingli.Dao.DistributorMarkDao;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.DistributorMark;
import cn.yiyingli.Service.DistributorMarkService;

public class DistributorMarkServiceImpl implements DistributorMarkService {

	private DistributorMarkDao distributorMarkDao;

	private DistributorDao distributorDao;

	public DistributorMarkDao getDistributorMarkDao() {
		return distributorMarkDao;
	}

	public void setDistributorMarkDao(DistributorMarkDao distributorMarkDao) {
		this.distributorMarkDao = distributorMarkDao;
	}

	public DistributorDao getDistributorDao() {
		return distributorDao;
	}

	public void setDistributorDao(DistributorDao distributorDao) {
		this.distributorDao = distributorDao;
	}

	@Override
	public void save(DistributorMark distributorMark) {
		getDistributorMarkDao().save(distributorMark);
	}

	@Override
	public void save(String distributorId, String UUID) {
		Distributor distributor = getDistributorDao().query(Long.valueOf(distributorId));
		DistributorMark dm = getDistributorMarkDao().queryUUID(Long.valueOf(distributorId));
		if (dm == null) {
			DistributorMark distributorMark = new DistributorMark();
			distributorMark.setDistributor(distributor);
			distributorMark.setUuid(UUID);
			save(distributorMark);
		} else {
			getDistributorMarkDao().update(distributorId, UUID);
		}
	}

	@Override
	public void remove(DistributorMark distributorMark) {
		getDistributorMarkDao().remove(distributorMark);
	}

	@Override
	public void remove(String UUID) {
		getDistributorMarkDao().remove(UUID);
	}

	@Override
	public DistributorMark query(String UUID) {
		return getDistributorMarkDao().query(UUID);
	}

	@Override
	public Distributor queryDistributor(String UUID) {
		return getDistributorMarkDao().queryDistributor(UUID);
	}

}
