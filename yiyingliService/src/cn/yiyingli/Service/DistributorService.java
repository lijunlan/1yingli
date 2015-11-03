package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Distributor;

public interface DistributorService {

	public static final int PAGE_SIZE = 10;

	void save(Distributor distributor);

	Long saveAndReturnId(Distributor distributor);

	void remove(Distributor distributor);

	void remove(long id);

	void merge(Distributor distributor);

	void update(Distributor distributor);

	Distributor queryByNo(String no);

	Distributor query(long id);

	Distributor queryByUsername(String username);

	Distributor queryByName(String name);

	long queryCount();

	List<Distributor> queryList(int page);

	List<Distributor> queryList(int page, int pageSize);

}
