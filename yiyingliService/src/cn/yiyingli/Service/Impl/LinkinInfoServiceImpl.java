package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.LinkinInfoDao;
import cn.yiyingli.Persistant.LinkinInfo;
import cn.yiyingli.Service.LinkinInfoService;

public class LinkinInfoServiceImpl implements LinkinInfoService {

	private LinkinInfoDao linkinInfoDao;

	public LinkinInfoDao getLinkinInfoDao() {
		return linkinInfoDao;
	}

	public void setLinkinInfoDao(LinkinInfoDao linkinInfoDao) {
		this.linkinInfoDao = linkinInfoDao;
	}

	@Override
	public void save(LinkinInfo linkinInfo) {
		getLinkinInfoDao().save(linkinInfo);
	}

	@Override
	public Long saveAndReturnId(LinkinInfo linkinInfo) {
		return getLinkinInfoDao().saveAndReturnId(linkinInfo);
	}

	@Override
	public void remove(LinkinInfo linkinInfo) {
		getLinkinInfoDao().remove(linkinInfo);
	}

	@Override
	public void remove(long id) {
		getLinkinInfoDao().remove(id);
	}

	@Override
	public void update(LinkinInfo linkinInfo) {
		getLinkinInfoDao().update(linkinInfo);
	}

	@Override
	public LinkinInfo query(long id, boolean lazy) {
		return getLinkinInfoDao().query(id, lazy);
	}

	@Override
	public List<LinkinInfo> queryList(int page, int pageSize, boolean lazy) {
		return getLinkinInfoDao().queryList(page, pageSize, lazy);
	}

	@Override
	public List<LinkinInfo> queryListByUserId(long userId, boolean lazy) {
		return getLinkinInfoDao().queryListByUserId(userId, lazy);
	}

	@Override
	public List<LinkinInfo> queryList(int page, boolean lazy) {
		return queryList(page, PAGE_SIZE_INT, lazy);
	}

}
