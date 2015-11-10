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
import cn.yiyingli.Util.MsgUtil;
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
		Map<String, Object> application = (Map<String, Object>) getData().get("application");
		String name = (String) application.get("name");
		String phone = (String) application.get("phone");
		String address = (String) application.get("address");
		String mail = (String) application.get("mail");

		List<Object> workExperiences = (List<Object>) application.get("workExperience");
		List<Object> studyExperiences = (List<Object>) application.get("studyExperience");
		Map<String, Object> service = (Map<String, Object>) application.get("service");
		List<Object> tips = (List<Object>) service.get("tips");
		Teacher teacher = PTeacherUtil.assembleTeacher(user, workExperiences, studyExperiences, tips, "", name, phone,
				address, mail, "", "", "false", "false", "false", "false", "false", "-1", "-1", "-1", "-1", "-1", "0",
				"0", getTipService());

		TService tService = new TService();
		tService.setAdvantage((String) service.get("advantage"));
		tService.setContent((String) service.get("content"));
		tService.setPriceTotal(Float.valueOf((String) service.get("price")));
		tService.setReason((String) service.get("reason"));
		tService.setTime(Float.valueOf((String) service.get("time")));
		tService.setTitle((String) service.get("title"));
		tService.setTeacher(teacher);
		teacher.settService(tService);
		ApplicationForm applicationForm = new ApplicationForm();
		applicationForm.setTeacher(teacher);
		applicationForm.setState(ApplicationFormService.APPLICATION_STATE_CHECKING_SHORT);
		applicationForm.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		applicationForm.setUser(user);
		if (user.getTeacherState() == UserService.TEACHER_STATE_CHECKING_SHORT) {
			setResMsg(MsgUtil.getErrorMsgByCode("15001"));
			return;
		} else if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
			setResMsg(MsgUtil.getErrorMsgByCode("15002"));
			return;
		} else {
			user.setTeacherState(UserService.TEACHER_STATE_CHECKING_SHORT);
			getApplicationFormService().save(applicationForm);
		}
		setResMsg(MsgUtil.getSuccessMsg("application has been received"));
	}

}
