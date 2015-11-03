package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Distributor;

public interface DistributorDao {

	void save(Distributor distributor);

	Long saveAndReturnId(Distributor distributor);

	void remove(Distributor distributor);

	void remove(long id);

	void merge(Distributor distributor);

	void update(Distributor distributor);

	Distributor queryByNo(String no);

	long queryCount();

	Distributor query(long id);

	Distributor queryByUsername(String username);

	Distributor queryByName(String name);

	List<Distributor> queryList(int page, int pageSize);
}
