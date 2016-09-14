package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.CheckNo;

public interface CheckNoDao {
	
	void save(CheckNo checkNo);

	Long saveAndReturnId(CheckNo checkNo);

	void remove(CheckNo checkNo);

	void remove(long id);

	void update(CheckNo checkNo);

	void updateFromSql(String sql);

	CheckNo query(long id);
	
	CheckNo query(String username);

	List<CheckNo> queryList();
}
