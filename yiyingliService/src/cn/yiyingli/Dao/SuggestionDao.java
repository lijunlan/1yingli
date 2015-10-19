package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Suggestion;

public interface SuggestionDao {

	void save(Suggestion suggestion);

	void remove(Suggestion suggestion);

	void remove(long id);

	void update(Suggestion suggestion);

	Suggestion query(long id);

	List<Suggestion> queryList();

	List<Suggestion> queryListByTime(int page, int pageSize);

}
