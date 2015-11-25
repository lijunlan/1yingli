package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTServiceUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;

public class CreateApplicationFormService extends UMsgService {

	private ApplicationFormService applicationFormService;

	private TipService tipService;

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

	@SuppressWarnings("unchecked")
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
		Map<String, Object> application = (Map<String, Object>) getData().get("application");
		String name = (String) application.get("name");
		String phone = (String) application.get("phone");
		String address = (String) application.get("address");
		String mail = (String) application.get("mail");

		List<Object> workExperiences = (List<Object>) application.get("workExperience");
		List<Object> studyExperiences = (List<Object>) application.get("studyExperience");
		Map<String, Object> service = (Map<String, Object>) application.get("service");
		List<Object> tips = (List<Object>) service.get("tips");
		Teacher teacher = PTeacherUtil.assembleTeacherByApplication(user, workExperiences, studyExperiences, tips, "",
				name, phone, address, mail, "", "", "false", "false", "false", "false", "false", "-1", "-1", "-1", "-1",
				"-1", "0", "0", getTipService());

		String advantage = (String) service.get("advantage");
		String content = (String) service.get("content");
		float price = Float.valueOf((String) service.get("price"));
		String reason = (String) service.get("reason");
		float time = Float.valueOf((String) service.get("time"));
		String title = (String) service.get("title");

		TService tService = new TService();
		PTServiceUtil.assembleWithTeacherByApplication(teacher, advantage, content, price, reason, time, title,
				tService);

		ApplicationForm applicationForm = new ApplicationForm();
		applicationForm.setTeacher(teacher);
		applicationForm.setState(ApplicationFormService.APPLICATION_STATE_CHECKING_SHORT);
		applicationForm.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		applicationForm.setUser(user);

		getApplicationFormService().save(applicationForm);

		setResMsg(MsgUtil.getSuccessMsg("application has been received"));
	}

}
