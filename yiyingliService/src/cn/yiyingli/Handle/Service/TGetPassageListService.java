package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
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
		if (state == PassageDao.PASSAGE_STATE_CHECKING) {
			count = getTeacher().getCheckPassageNumber();
		} else if (state == PassageDao.PASSAGE_STATE_OK) {
			count = getTeacher().getPassageNumber();
		} else if (state == PassageDao.PASSAGE_STATE_REFUSE) {
			count = getTeacher().getRefusePassageNumber();
		} else {
			count = 0L;
		}
		List<Passage> passages = getPassageService().queryListByTeacherAndState(page, PassageService.PAGE_SIZE_TEACHER,
				getTeacher().getId(), state);
		ExList sends = new ExArrayList();
		for (Passage p : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleSimple(p, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("data", sends).put("count", count);
		setResMsg(toSend.finishByJson());
	}

}
