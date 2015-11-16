package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Suggestion;

public interface SuggestionService {

	public static final int PAGE_SIZE = 12;

	void save(Suggestion suggestion);

	void remove(Suggestion suggestion);

	void remove(long id);

	void update(Suggestion suggestion);

	Suggestion query(long id);

	List<Suggestion> queryList();

	List<Suggestion> queryListByTime(int page);
	
	List<Suggestion> queryListByTime(int page, int pageSize);

}
