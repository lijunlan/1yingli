package cn.yiyingli.Handle.Service;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;

public class TRemovePassageService extends TMsgService {

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
		passage.setRemove(true);
		Teacher teacher = passage.getOwnTeacher();
		switch (passage.getState()) {
		case PassageDao.PASSAGE_STATE_REFUSE:
			teacher.setPassageNumber(teacher.getRefusePassageNumber() - 1);
			break;
		case PassageDao.PASSAGE_STATE_OK:
			teacher.setPassageNumber(teacher.getPassageNumber() - 1);
			break;
		case PassageDao.PASSAGE_STATE_CHECKING:
			teacher.setPassageNumber(teacher.getCheckPassageNumber() - 1);
			break;
		default:
			break;
		}
		getPassageService().update(passage, true);
		setResMsg(MsgUtil.getSuccessMsg("remove passage successfully"));
	}

}
