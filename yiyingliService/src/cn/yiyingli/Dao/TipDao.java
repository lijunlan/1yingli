package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Tip;

public interface TipDao {
	
	void save(Tip tip);

	Long saveAndReturnId(Tip tip);

	void remove(Tip tip);

	void remove(long id);

	void update(Tip tip);

	void updateFromSql(String sql);

	Tip query(long id);

	List<Tip> queryList();
	
	List<Tip> queryNormalList();
}
