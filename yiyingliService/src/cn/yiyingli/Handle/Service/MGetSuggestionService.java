package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Suggestion;
import cn.yiyingli.Service.SuggestionService;
import cn.yiyingli.Util.MsgUtil;

public class MGetSuggestionService extends MMsgService {

	private SuggestionService suggestionService;

	public SuggestionService getSuggestionService() {
		return suggestionService;
	}

	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = getData().getInt("page");
		List<Suggestion> suggestions = getSuggestionService().queryListByTime(page);
		ExList sendList = new ExArrayList();
		for (Suggestion s : suggestions) {
			SuperMap map = new SuperMap();
			map.put("id", s.getId());
			map.put("ip", s.getIp());
			map.put("content", s.getContent());
			map.put("time", s.getCreateTime());
			sendList.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", sendList).finishByJson());
	}

}
