package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.SuggestionDao;
import cn.yiyingli.Persistant.Suggestion;
import cn.yiyingli.Service.SuggestionService;

public class SuggestionServiceImpl implements SuggestionService {

	private SuggestionDao suggestionDao;

	public SuggestionDao getSuggestionDao() {
		return suggestionDao;
	}

	public void setSuggestionDao(SuggestionDao suggestionDao) {
		this.suggestionDao = suggestionDao;
	}

	@Override
	public void save(Suggestion suggestion) {
		getSuggestionDao().save(suggestion);
	}

	@Override
	public void remove(Suggestion suggestion) {
		getSuggestionDao().remove(suggestion);
	}

	@Override
	public void remove(long id) {
		getSuggestionDao().remove(id);
	}

	@Override
	public void update(Suggestion suggestion) {
		getSuggestionDao().update(suggestion);
	}

	@Override
	public Suggestion query(long id) {
		return getSuggestionDao().query(id);
	}

	@Override
	public List<Suggestion> queryList() {
		return getSuggestionDao().queryList();
	}

	@Override
	public List<Suggestion> queryListByTime(int page, int pageSize) {
		return getSuggestionDao().queryListByTime(page, pageSize);
	}

	@Override
	public List<Suggestion> queryListByTime(int page) {
		return getSuggestionDao().queryListByTime(page, PAGE_SIZE);
	}

}
