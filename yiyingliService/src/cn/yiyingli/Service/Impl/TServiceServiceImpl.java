package cn.yiyingli.Service.Impl;

import cn.yiyingli.Dao.TServiceDao;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Service.TServiceService;

public class TServiceServiceImpl implements TServiceService {

	private TServiceDao tServiceDao;

	public TServiceDao gettServiceDao() {
		return tServiceDao;
	}

	public void settServiceDao(TServiceDao tServiceDao) {
		this.tServiceDao = tServiceDao;
	}

	@Override
	public void save(TService tService) {
		gettServiceDao().save(tService);
	}

	@Override
	public Long saveAndReturnId(TService tService) {
		return gettServiceDao().saveAndReturnId(tService);
	}

	@Override
	public void remove(TService tService) {
		gettServiceDao().remove(tService);
	}

	@Override
	public void remove(long id) {
		gettServiceDao().remove(id);
	}

	@Override
	public void update(TService tService) {
		gettServiceDao().update(tService);
	}

	@Override
	public TService query(long id, boolean lazy) {
		return gettServiceDao().query(id, lazy);
	}

	@Override
	public TService queryByTeacherStoreId(long teacherStoreId, boolean lazy) {
		return gettServiceDao().queryByTeacherStoreId(teacherStoreId, lazy);
	}

}
