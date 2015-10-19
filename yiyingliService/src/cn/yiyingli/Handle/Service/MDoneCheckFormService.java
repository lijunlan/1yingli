package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CheckFormService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class MDoneCheckFormService extends MsgService {

	private CheckFormService checkFormService;

	private ManagerMarkService managerMarkService;

	public CheckFormService getCheckFormService() {
		return checkFormService;
	}

	public void setCheckFormService(CheckFormService checkFormService) {
		this.checkFormService = checkFormService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("cfId") && getData().containsKey("accept")
				&& getData().containsKey("description");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		String cfId = (String) getData().get("cfId");
		String accept = (String) getData().get("accept");
		String description = (String) getData().get("description");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		CheckForm checkForm = getCheckFormService().query(Long.valueOf(cfId));
		if (checkForm == null) {
			setResMsg(MsgUtil.getErrorMsg("checkForm is not existed"));
			return;
		}
		boolean acpt = Boolean.valueOf(accept);
		Teacher teacher = checkForm.getTeacher();
		if(checkForm.getKind()!=CheckFormService.CHECK_STATE_CHECKING_SHORT){
			setResMsg(MsgUtil.getErrorMsg("checkForm has been deal"));
		}
		if (acpt) {
			if (checkForm.getKind() == CheckFormService.CHECK_KIND_COMPANY_SHORT) {
				teacher.setCheckWorkState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
			} else if (checkForm.getKind() == CheckFormService.CHECK_KIND_IDENTITY_SHORT) {
				teacher.setCheckIDCardState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
			} else if (checkForm.getKind() == CheckFormService.CHECK_KIND_SCHOOL_SHORT) {
				teacher.setCheckDegreeState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
			} else {
				setResMsg(MsgUtil.getErrorMsg("error"));
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
				setResMsg(MsgUtil.getErrorMsg("error"));
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
