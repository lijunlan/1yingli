package cn.yiyingli.Service;

import cn.yiyingli.Persistant.TService;

public interface TServiceService {
	void save(TService tService);

	Long saveAndReturnId(TService tService);

	void remove(TService tService);

	void remove(long id);

	void update(TService tService);

	TService query(long id, boolean lazy);

	TService queryByTeacherStoreId(long teacherStoreId, boolean lazy);
}
