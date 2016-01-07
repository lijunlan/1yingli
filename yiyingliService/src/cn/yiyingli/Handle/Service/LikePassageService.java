package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

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
		boolean result = getPassageService().updateUserLike(passage, getUser());
		if (result) {
			setResMsg(MsgUtil.getSuccessMsg("like passage successfully"));
			SendMsgToBaiduUtil.updatePassageUserTrainDataLike(getUser().getId() + "", passage.getId() + "",
					Calendar.getInstance().getTimeInMillis() + "");
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("12021"));
		}
	}

}
