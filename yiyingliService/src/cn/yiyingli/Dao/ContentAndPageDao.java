package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.ContentAndPage;

public interface ContentAndPageDao {

	void save(ContentAndPage contentAndPage);

	void remove(long contentAndPageId);

	void update(ContentAndPage contentAndPage);

	List<ContentAndPage> queryListByPages(long pagesId);

	List<ContentAndPage> queryListWithTeacherByKey(String activityKey, int page, int pageSize);

	List<ContentAndPage> queryListWithPassageByKey(String activityKey, int page, int pageSize);

	List<ContentAndPage> queryListWithServiceProByKey(String activityKey, int page, int pageSize);
}
