package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;

public class MValidatePassageService extends MMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("validate") && getData().containsKey("passageId")
				|| getData().containsKey("refuseReason");
	}

	@Override
	public void doit() {
		long passageId = Long.valueOf((String) getData().get("passageId"));
		boolean validate = Boolean.valueOf((String) getData().get("validate"));
		Passage passage = getPassageService().query(passageId);
		if (passage == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22006"));
			return;
		}
		if (validate) {
			passage.setState(PassageService.PASSAGE_STATE_OK);
			passage.setOnshow(true);
		} else {
			passage.setState(PassageService.PASSAGE_STATE_REFUSE);
			passage.setOnshow(false);
			if (getData().containsKey("refuseReason")) {
				String refuseReason = (String) getData().get("refuseReason");
				passage.setRefuseReason(refuseReason);
			}
		}
		getPassageService().update(passage);
		setResMsg(MsgUtil.getSuccessMsg("validate passage successfully"));
	}

}
