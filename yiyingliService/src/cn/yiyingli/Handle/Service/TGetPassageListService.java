package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class TGetPassageListService extends TMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("page") && getData().containsKey("state");
	}

	@Override
	public void doit() {
		int page = Integer.valueOf((String) getData().get("page"));
		short state = Short.valueOf((String) getData().get("state"));
		long count = 0L;
		if (state == PassageService.PASSAGE_STATE_CHECKING) {
			count = getTeacher().getCheckPassageNumber();
		} else if (state == PassageService.PASSAGE_STATE_OK) {
			count = getTeacher().getPassageNumber();
		} else if (state == PassageService.PASSAGE_STATE_REFUSE) {
			count = getTeacher().getRefusePassageNumber();
		} else {
			count = 0L;
		}
		List<Passage> passages = getPassageService().queryListByTeacherAndState(page, PassageService.PAGE_SIZE_TEACHER,
				getTeacher().getId(), state);
		List<String> sends = new ArrayList<String>();
		for (Passage p : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleSimple(p, map);
			sends.add(map.finishByJson());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("data", Json.getJson(sends)).put("count", count);
		setResMsg(toSend.finishByJson());
	}

}
