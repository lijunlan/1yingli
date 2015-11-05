package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CheckFormService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class CreateCheckFormService extends TMsgService {

	private CheckFormService checkFormService;

	public CheckFormService getCheckFormService() {
		return checkFormService;
	}

	public void setCheckFormService(CheckFormService checkFormService) {
		this.checkFormService = checkFormService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && (getData().containsKey("idcardUrl") || getData().containsKey("studyevidences")
				|| getData().containsKey("workInfo"));
	}

	@Override
	public void doit() {
		super.doit();
		Teacher teacher = getTeacher();
		CheckForm checkForm = new CheckForm();
		if (getData().containsKey("idcardUrl")) {
			checkForm.setKind(CheckFormService.CHECK_KIND_IDENTITY_SHORT);
			checkForm.setUrl((String) getData().get("idcardUrl"));
			teacher.setCheckIDCardState(TeacherService.CHECK_STATE_CHECKING_SHORT);
		} else if (getData().containsKey("studyevidences")) {
			checkForm.setKind(CheckFormService.CHECK_KIND_SCHOOL_SHORT);
			checkForm.setUrl((String) getData().get("studyevidences"));
			teacher.setCheckDegreeState(TeacherService.CHECK_STATE_CHECKING_SHORT);
		} else {
			checkForm.setKind(CheckFormService.CHECK_KIND_COMPANY_SHORT);
			checkForm.setUrl((String) getData().get("workInfo"));
			teacher.setCheckWorkState(TeacherService.CHECK_STATE_CHECKING_SHORT);
		}
		checkForm.setState(CheckFormService.CHECK_STATE_CHECKING_SHORT);
		checkForm.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		checkForm.setTeacher(teacher);
		getCheckFormService().save(checkForm);
		setResMsg(MsgUtil.getSuccessMsg("apply check successfully"));
	}

}
