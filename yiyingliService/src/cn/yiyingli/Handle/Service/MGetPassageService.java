package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;

public class MGetPassageService extends MMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("passageId");
	}

	@Override
	public void doit() {
		Passage passage = getPassageService().queryWithTeacherByManager(getData().getLong("passageId"));
		if (passage == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22006"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExPassage.assembleForManager(passage, map);

		setResMsg(map.finishByJson());
	}

}
