package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CreateApplicationFormService extends UMsgService {

	private ApplicationFormService applicationFormService;

	private TipService tipService;

	private TeacherService teacherService;


	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("application");
	}

	@Override
	public void doit() {
		User user = getUser();
		if (user.getTeacherState().shortValue() == UserService.TEACHER_STATE_CHECKING_SHORT) {
			LogUtil.error("15001,userId:" + user.getId() + " teacher state is:" + user.getTeacherState(), getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("15001"));
			return;
		} else if (user.getTeacherState().shortValue() == UserService.TEACHER_STATE_ON_SHORT) {
			LogUtil.error("15002,userId:" + user.getId() + " teacher state is:" + user.getTeacherState(), getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("15002"));
			return;
		}

		JSONObject application = getData().getJSONObject("application");
		String name = application.getString("name");
		String contact = application.getString("contact");
		String phone = application.getString("phone");
		String address = application.getString("address");
		String mail = application.getString("mail");

		JSONArray workExperiences = application.getJSONArray("workExperience");
		JSONArray studyExperiences = application.getJSONArray("studyExperience");

		Teacher teacher = PTeacherUtil.assembleTeacherByApplication(user, workExperiences, studyExperiences,
				new JSONArray(), "", name, phone, address, mail, "", "", "false", "false", "false", "false", "false",
				"", 0F, getTipService());

		String invitationCode = application.getString("invitationCode");
		if (invitationCode != "") {
			Teacher inviter = getTeacherService().queryByInvitationCode(invitationCode);
			teacher.setInviter(inviter);
		}

		ApplicationForm applicationForm = new ApplicationForm();
		applicationForm.setContact(contact);
		applicationForm.setTeacher(teacher);
		applicationForm.setState(ApplicationFormService.APPLICATION_STATE_CHECKING_SHORT);
		applicationForm.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		applicationForm.setUser(user);

		getApplicationFormService().save(applicationForm);

		setResMsg(MsgUtil.getSuccessMsg("application has been received"));
	}

}
