package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.PagesDao;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;

public class PagesServiceImpl implements PagesService {

	private PagesDao pagesDao;

	public PagesDao getPagesDao() {
		return pagesDao;
	}

	public void setPagesDao(PagesDao pagesDao) {
		this.pagesDao = pagesDao;
	}

	@Override
	public void save(Pages pages) {
		getPagesDao().save(pages);
	}

	@Override
	public void remove(Pages pages) {
		getPagesDao().remove(pages);
	}

	@Override
	public void update(Pages pages) {
		getPagesDao().update(pages);
	}

	@Override
	public Pages query(Long id) {
		return getPagesDao().query(id);
	}

	@Override
	public Pages queryByKey(String activityKey) {
		return getPagesDao().queryByKey(activityKey);
	}

	@Override
	public List<Pages> queryList(int page, int pageSize) {
		return getPagesDao().queryList(page, pageSize);
	}

	@Override
	public List<Pages> queryList(int page) {
		return getPagesDao().queryList(page, SIZE_MANAGER_PAGE);
	}

	@Override
	public List<Pages> queryListOrderByWeight(int page,int pageSize) {
		return getPagesDao().queryListOrderByWeight(page, pageSize);
	}
}
