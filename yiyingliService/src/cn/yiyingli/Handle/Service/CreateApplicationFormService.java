package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class CreateApplicationFormService extends MsgService {

	private ApplicationFormService applicationFormService;

	private UserMarkService userMarkService;

	private TipService tipService;

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("application");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Map<String, Object> application = (Map<String, Object>) getData().get("application");
		String name = (String) application.get("name");
		String phone = (String) application.get("phone");
		String address = (String) application.get("address");
		String mail = (String) application.get("mail");
		if (name == null || phone == null || address == null || mail == null) {
			setResMsg(MsgUtil.getErrorMsg("data is not complete"));
		}
		List<Object> workExperiences = (List<Object>) application.get("workExperience");
		List<Object> studyExperiences = (List<Object>) application.get("studyExperience");
		Map<String, Object> service = (Map<String, Object>) application.get("service");
		List<Object> tips = (List<Object>) service.get("tips");
		Teacher teacher = new Teacher();
		teacher.setUser(user);
		teacher.setPhone(phone);
		user.setAddress(address);
		user.setEmail(mail);
		user.setName(name);
		teacher.setOnService(false);
		teacher.setUsername(user.getUsername());
		teacher.setAddress(address);
		teacher.setName(name);
		teacher.setEmail(mail);
		teacher.setCheckDegreeState(TeacherService.CHECK_STATE_NONE_SHORT);
		teacher.setCheckEmail(false);
		teacher.setCheckIDCardState(TeacherService.CHECK_STATE_NONE_SHORT);
		teacher.setCheckPhone(false);
		teacher.setCheckWorkState(TeacherService.CHECK_STATE_NONE_SHORT);
		teacher.setLevel((short) 0);
		teacher.setOrderNumber(0L);
		teacher.setLikeNumber(0L);
		teacher.setCommentNumber(0L);
		// teacher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		List<WorkExperience> wes = new ArrayList<WorkExperience>();
		for (Object we : workExperiences) {
			WorkExperience workExperience = new WorkExperience();
			workExperience.setCompanyName((String) ((Map<String, Object>) we).get("companyName"));
			workExperience.setDescription((String) ((Map<String, Object>) we).get("description"));
			workExperience.setEndTime((String) ((Map<String, Object>) we).get("endTime"));
			workExperience.setStartTime((String) ((Map<String, Object>) we).get("startTime"));
			workExperience.setPosition((String) ((Map<String, Object>) we).get("position"));
			workExperience.setOwnTeacher(teacher);
			wes.add(workExperience);
		}
		teacher.setWorkExperiences(wes);
		List<StudyExperience> ses = new ArrayList<StudyExperience>();
		for (Object se : studyExperiences) {
			StudyExperience studyExperience = new StudyExperience();
			studyExperience.setDegree((String) ((Map<String, Object>) se).get("degree"));
			studyExperience.setDescription((String) ((Map<String, Object>) se).get("description"));
			studyExperience.setEndTime((String) ((Map<String, Object>) se).get("endTime"));
			studyExperience.setMajor((String) ((Map<String, Object>) se).get("major"));
			studyExperience.setSchoolName((String) ((Map<String, Object>) se).get("schoolName"));
			studyExperience.setStartTime((String) ((Map<String, Object>) se).get("startTime"));
			studyExperience.setOwnTeacher(teacher);
			ses.add(studyExperience);
		}
		teacher.setStudyExperiences(ses);
		long tipMark = 0;
		for (Object t : tips) {
			Long tid = Long.valueOf((String) ((Map<String, Object>) t).get("id"));
			tipMark = tipMark | tid;
			Tip mT = getTipService().query(tid);
			teacher.getTips().add(mT);
		}
		teacher.setTipMark(tipMark);
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
			setResMsg(MsgUtil.getErrorMsg("you have applied"));
			return;
		} else if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
			setResMsg(MsgUtil.getErrorMsg("you are already teacher"));
			return;
		} else {
			user.setTeacherState(UserService.TEACHER_STATE_CHECKING_SHORT);
			getApplicationFormService().save(applicationForm);
		}
		setResMsg(MsgUtil.getSuccessMsg("application has been received"));
	}

}
