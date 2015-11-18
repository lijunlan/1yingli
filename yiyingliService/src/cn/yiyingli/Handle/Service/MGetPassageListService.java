package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetPassageListService extends MMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		// contain state
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = Integer.valueOf((String) getData().get("page"));
		List<Passage> passages = null;
		if (!getData().containsKey("state")) {
			passages = getPassageService().queryList(page);
		} else {
			short state = Short.valueOf((String) getData().get("state"));
			passages = getPassageService().queryListByState(page, state);
		}
		List<String> sends = new ArrayList<String>();
		for (Passage p : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleForManager(p, map);
			sends.add(map.finishByJson());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("data", Json.getJson(sends));
		setResMsg(toSend.finishByJson());
	}

}
