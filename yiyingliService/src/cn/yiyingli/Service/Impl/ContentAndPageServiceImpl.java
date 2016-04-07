package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.ContentAndPageDao;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Service.ContentAndPageService;

public class ContentAndPageServiceImpl implements ContentAndPageService {

	private ContentAndPageDao contentAndPageDao;

	public ContentAndPageDao getContentAndPageDao() {
		return contentAndPageDao;
	}

	public void setContentAndPageDao(ContentAndPageDao contentAndPageDao) {
		this.contentAndPageDao = contentAndPageDao;
	}

	@Override
	public void save(ContentAndPage contentAndPage) {
		getContentAndPageDao().save(contentAndPage);
	}

	@Override
	public void remove(long contentAndPageId) {
		getContentAndPageDao().remove(contentAndPageId);
	}

	@Override
	public void update(ContentAndPage contentAndPage) {
		getContentAndPageDao().update(contentAndPage);
	}

	@Override
	public ContentAndPage query(long contentAndPageId) {
		return getContentAndPageDao().query(contentAndPageId);
	}

	@Override
	public List<ContentAndPage> queryListByPages(long pagesId) {
		return getContentAndPageDao().queryListByPages(pagesId);
	}

	@Override
	public List<ContentAndPage> queryListWithTeacherByKey(String activityKey, int page, int pageSize) {
		return getContentAndPageDao().queryListWithTeacherByKey(activityKey, page, pageSize);
	}

	@Override
	public List<ContentAndPage> queryListWithPassageByKey(String activityKey, int page, int pageSize) {
		return getContentAndPageDao().queryListWithPassageByKey(activityKey, page, pageSize);
	}

	@Override
	public List<ContentAndPage> queryListWithServiceProByKey(String activityKey, int page, int pageSize) {
		return getContentAndPageDao().queryListWithServiceProByKey(activityKey, page, pageSize);
	}

	@Override
	public List<ContentAndPage> queryListWithTeacherOrderByTeacherMile(int page, int pageSize) {
		return getContentAndPageDao().queryListWithTeacher(page, pageSize);
	}
}
