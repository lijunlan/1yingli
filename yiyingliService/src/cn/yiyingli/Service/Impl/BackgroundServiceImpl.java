package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.BackgroundDao;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Service.BackgroundService;

public class BackgroundServiceImpl implements BackgroundService {

	private BackgroundDao backgroundDao;

	public BackgroundDao getBackgroundDao() {
		return backgroundDao;
	}

	public void setBackgroundDao(BackgroundDao backgroundDao) {
		this.backgroundDao = backgroundDao;
	}

	@Override
	public void save(Background background) {
		getBackgroundDao().save(background);
	}

	@Override
	public Long saveAndReturnId(Background background) {
		return getBackgroundDao().saveAndReturnId(background);
	}

	@Override
	public void remove(Background background) {
		getBackgroundDao().remove(background);
	}

	@Override
	public void remove(long id) {
		getBackgroundDao().remove(id);
	}

	@Override
	public void update(Background background) {
		getBackgroundDao().update(background);
	}

	@Override
	public Background query(long id) {
		return getBackgroundDao().query(id);
	}

	@Override
	public List<Background> queryList() {
		return getBackgroundDao().queryList();
	}

	@Override
	public List<Background> queryList(int page, int pageSize) {
		return getBackgroundDao().queryList(page, pageSize);
	}

}
