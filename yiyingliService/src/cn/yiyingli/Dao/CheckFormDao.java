package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.CheckForm;

public interface CheckFormDao {

	void save(CheckForm checkForm);

	Long saveAndReturnId(CheckForm checkForm);

	void remove(CheckForm checkForm);

	void remove(long id);

	void update(CheckForm checkForm);

	void updateFromSql(String sql);

	CheckForm query(long id);

	CheckForm query(String teacherId);

	List<CheckForm> queryList();
}
