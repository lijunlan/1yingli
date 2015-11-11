package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CheckFormService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class MDoneCheckFormService extends MMsgService {

	private CheckFormService checkFormService;

	public CheckFormService getCheckFormService() {
		return checkFormService;
	}

	public void setCheckFormService(CheckFormService checkFormService) {
		this.checkFormService = checkFormService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("cfId") && getData().containsKey("accept")
				&& getData().containsKey("description");
	}

	@Override
	public void doit() {
		Manager manager = getManager();
		String cfId = (String) getData().get("cfId");
		String accept = (String) getData().get("accept");
		String description = (String) getData().get("description");
		CheckForm checkForm = getCheckFormService().query(Long.valueOf(cfId));
		if (checkForm == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32005"));
			return;
		}
		boolean acpt = Boolean.valueOf(accept);
		Teacher teacher = checkForm.getTeacher();
		if (checkForm.getKind() != CheckFormService.CHECK_STATE_CHECKING_SHORT) {
			setResMsg(MsgUtil.getErrorMsgByCode("32006"));
		}
		if (acpt) {
			if (checkForm.getKind() == CheckFormService.CHECK_KIND_COMPANY_SHORT) {
				teacher.setCheckWorkState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
			} else if (checkForm.getKind() == CheckFormService.CHECK_KIND_IDENTITY_SHORT) {
				teacher.setCheckIDCardState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
			} else if (checkForm.getKind() == CheckFormService.CHECK_KIND_SCHOOL_SHORT) {
				teacher.setCheckDegreeState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("32007"));
			}
			checkForm.setState(CheckFormService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			if (checkForm.getKind() == CheckFormService.CHECK_KIND_COMPANY_SHORT) {
				teacher.setCheckWorkState(TeacherService.CHECK_STATE_NONE_SHORT);
			} else if (checkForm.getKind() == CheckFormService.CHECK_KIND_IDENTITY_SHORT) {
				teacher.setCheckIDCardState(TeacherService.CHECK_STATE_NONE_SHORT);
			} else if (checkForm.getKind() == CheckFormService.CHECK_KIND_SCHOOL_SHORT) {
				teacher.setCheckDegreeState(TeacherService.CHECK_STATE_NONE_SHORT);
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("32008"));
			}
			checkForm.setState(CheckFormService.CHECK_STATE_FAILED_SHORT);
		}
		checkForm.setCheckInfo(description);
		checkForm.setEndManager(manager);
		checkForm.setEndTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		getCheckFormService().update(checkForm);
		setResMsg(MsgUtil.getSuccessMsg("checkform has been deal"));
	}

}
