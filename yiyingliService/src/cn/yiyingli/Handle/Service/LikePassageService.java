package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;

public class LikePassageService extends UMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("pid");
	}

	@Override
	public void doit() {
		long passageId = Long.valueOf(getData().getString("pid"));
		Passage passage = getPassageService().query(passageId);
		if (passage == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("12020"));
			return;
		}
		getPassageService().updateUserLike(passage, getUser());
		setResMsg(MsgUtil.getSuccessMsg("like passage successfully"));
	}

}
