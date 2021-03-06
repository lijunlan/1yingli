package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;

public class TGetPassageService extends TMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("passageId");
	}

	@Override
	public void doit() {
		long passageId = Long.valueOf((String) getData().get("passageId"));
		Passage passage = getPassageService().queryWithTeacherById(passageId);
		if (passage == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22006"));
			return;
		}
		if (passage.getOwnTeacher().getId().longValue() != getTeacher().getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsgByCode("22007"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExPassage.assembleDetail(passage, map);
		setResMsg(map.finishByJson());
	}

}
