package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.TService;

public interface TServiceDao {

	void save(TService tService);

	Long saveAndReturnId(TService tService);

	void remove(TService tService);

	void remove(long id);

	void update(TService tService);

	void updateFromSql(String sql);

	TService query(long id, boolean lazy);

	TService queryByTeacherStoreId(long teacherStoreId, boolean lazy);
}
