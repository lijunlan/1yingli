package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.DistributorMark;

public interface DistributorMarkDao {

	void save(DistributorMark distributorMark);

	void remove(DistributorMark distributorMark);

	void remove(String UUID);

	void update(DistributorMark distributorMark);

	void update(String distributorId, String UUID);

	DistributorMark queryUUID(long distributorId);

	DistributorMark query(String UUID);

	Distributor queryDistributor(String UUID);
}
