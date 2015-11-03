package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.DistributorDao;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Service.DistributorService;

public class DistributorServiceImpl implements DistributorService {

	private DistributorDao distributorDao;

	public DistributorDao getDistributorDao() {
		return distributorDao;
	}

	public void setDistributorDao(DistributorDao distributorDao) {
		this.distributorDao = distributorDao;
	}

	@Override
	public void save(Distributor distributor) {
		getDistributorDao().save(distributor);
	}

	@Override
	public Long saveAndReturnId(Distributor distributor) {
		return getDistributorDao().saveAndReturnId(distributor);
	}

	@Override
	public void remove(Distributor distributor) {
		getDistributorDao().remove(distributor);
	}

	@Override
	public void remove(long id) {
		getDistributorDao().remove(id);
	}

	@Override
	public void merge(Distributor distributor) {
		getDistributorDao().merge(distributor);
	}

	@Override
	public void update(Distributor distributor) {
		getDistributorDao().update(distributor);
	}

	@Override
	public Distributor queryByNo(String no) {
		return getDistributorDao().queryByNo(no);
	}

	@Override
	public Distributor query(long id) {
		return getDistributorDao().query(id);
	}

	@Override
	public Distributor queryByUsername(String username) {
		return getDistributorDao().queryByUsername(username);
	}

	@Override
	public Distributor queryByName(String name) {
		return getDistributorDao().queryByName(name);
	}

	@Override
	public long queryCount() {
		return getDistributorDao().queryCount();
	}

	@Override
	public List<Distributor> queryList(int page) {
		return getDistributorDao().queryList(page, PAGE_SIZE);
	}

	@Override
	public List<Distributor> queryList(int page, int pageSize) {
		return getDistributorDao().queryList(page, pageSize);
	}

}
