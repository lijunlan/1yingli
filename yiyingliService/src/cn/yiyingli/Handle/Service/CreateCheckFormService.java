package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CheckFormService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class CreateCheckFormService extends MsgService {

	private CheckFormService checkFormService;

	private TeacherService teacherService;

	private UserMarkService userMarkService;

	public CheckFormService getCheckFormService() {
		return checkFormService;
	}

	public void setCheckFormService(CheckFormService checkFormService) {
		this.checkFormService = checkFormService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("teacherId") && (getData().containsKey("idcardUrl")
				|| getData().containsKey("studyevidences") || getData().containsKey("workInfo"));
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Teacher teacher = getTeacherService().queryByUserIdWithTService(user.getId(), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("you are not a teacher"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsg("uid don't match teacherId"));
			return;
		}
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
		setResMsg(MsgUtil.getSuccessMsg("申请验证成功"));
	}

}
