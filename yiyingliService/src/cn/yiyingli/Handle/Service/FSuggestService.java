package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Suggestion;
import cn.yiyingli.Service.SuggestionService;
import cn.yiyingli.Util.MsgUtil;

public class FSuggestService extends MsgService {

	private SuggestionService suggestionService;

	public SuggestionService getSuggestionService() {
		return suggestionService;
	}

	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("content");
	}

	@Override
	public void doit() {
		String content = (String) getData().get("content");
		String ip = (String) getData().get("IP");
		String createTime = Calendar.getInstance().getTimeInMillis() + "";
		Suggestion suggestion = new Suggestion();
		suggestion.setContent(content);
		suggestion.setCreateTime(createTime);
		suggestion.setIp(ip);
		getSuggestionService().save(suggestion);
		setResMsg(MsgUtil.getSuccessMsg("thank you for suggestion"));
	}

}
