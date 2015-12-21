package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
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
		ExList sends = new ExArrayList();
		for (Passage p : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleForManager(p, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}

}
