package cn.yiyingli.Service;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.DistributorMark;

public interface DistributorMarkService {

	void save(DistributorMark distributorMark);

	void save(String distributorId, String UUID);

	void remove(DistributorMark distributorMark);

	void remove(String UUID);

	DistributorMark query(String UUID);

	Distributor queryDistributor(String UUID);
}
