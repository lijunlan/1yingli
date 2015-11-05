package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MCreateTeacherService extends MMsgService {

	private TeacherService teacherService;

	private UserService userService;

	private TipService tipService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("teacher") && getData().containsKey("username");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doit() {
		super.doit();
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32002"));
			return;
		}

		Map<String, Object> tdata = (Map<String, Object>) getData().get("teacher");
		List<Object> workExperiences = (List<Object>) tdata.get("workExperience");
		List<Object> studyExperiences = (List<Object>) tdata.get("studyExperience");
		Map<String, Object> service = (Map<String, Object>) tdata.get("service");
		List<Object> tips = (List<Object>) service.get("tips");

		Teacher teacher = new Teacher();
		String simpleinfo = (String) tdata.get("simpleinfo");
		String name = (String) tdata.get("name");
		String phone = (String) tdata.get("phone");
		String address = (String) tdata.get("address");
		String mail = (String) tdata.get("email");
		String iconUrl = (String) tdata.get("iconUrl");
		String introduce = (String) tdata.get("introduce");
		String checkPhone = (String) tdata.get("checkPhone");
		String checkIDCard = (String) tdata.get("checkIDCard");
		String checkEmail = (String) tdata.get("checkEmail");
		String checkWork = (String) tdata.get("checkWork");
		String checkStudy = (String) tdata.get("checkStudy");
		String showWeight1 = (String) tdata.get("showWeight1");
		String showWeight2 = (String) tdata.get("showWeight2");
		String showWeight4 = (String) tdata.get("showWeight4");
		String showWeight8 = (String) tdata.get("showWeight8");
		String showWeight16 = (String) tdata.get("showWeight16");

		if (Boolean.valueOf(checkPhone)) {
			teacher.setCheckPhone(true);
		} else {
			teacher.setCheckPhone(false);
		}

		if (Boolean.valueOf(checkIDCard)) {
			teacher.setCheckIDCardState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			teacher.setCheckIDCardState(TeacherService.CHECK_STATE_NONE_SHORT);
		}

		if (Boolean.valueOf(checkEmail)) {
			teacher.setCheckEmail(true);
		} else {
			teacher.setCheckEmail(false);
		}

		if (Boolean.valueOf(checkWork)) {
			teacher.setCheckWorkState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			teacher.setCheckWorkState(TeacherService.CHECK_STATE_NONE_SHORT);
		}

		if (Boolean.valueOf(checkStudy)) {
			teacher.setCheckDegreeState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			teacher.setCheckDegreeState(TeacherService.CHECK_STATE_NONE_SHORT);
		}

		user.setTeacherState(UserService.TEACHER_STATE_ON_SHORT);
		user.setAddress(address);
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(mail);
		user.setIconUrl(iconUrl);
		// user.setResume(introduce);
		user.setTeacher(teacher);
		teacher.setUser(user);

		teacher.setAddress(address);
		teacher.setUsername(user.getUsername());
		teacher.setName(name);
		teacher.setPhone(phone);
		teacher.setEmail(mail);
		teacher.setIconUrl(iconUrl);
		teacher.setIntroduce(introduce);
		teacher.setSimpleInfo(simpleinfo);
		teacher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		teacher.setShowWeight1(Integer.valueOf(showWeight1.equals("") ? "" : showWeight1));
		teacher.setShowWeight2(Integer.valueOf(showWeight2.equals("") ? "" : showWeight2));
		teacher.setShowWeight4(Integer.valueOf(showWeight4.equals("") ? "" : showWeight4));
		teacher.setShowWeight8(Integer.valueOf(showWeight8.equals("") ? "" : showWeight8));
		teacher.setShowWeight16(Integer.valueOf(showWeight16.equals("") ? "" : showWeight16));

		teacher.setCommentNumber(0L);
		teacher.setLevel((short) 5);
		teacher.setOrderNumber(0L);
		teacher.setLikeNumber(0L);
		teacher.setTipMark(0L);
		teacher.setOnService(true);

		TService tService = new TService();
		String serviceTitle = (String) service.get("title");
		String serviceTime = (String) service.get("time");
		String servicePrice = (String) service.get("price");
		String serviceTimePerWeek = (String) service.get("timeperweek");
		String serviceContent = (String) service.get("content");

		tService.setTitle(serviceTitle);
		tService.setTime(Float.valueOf(serviceTime));
		tService.setPriceTotal(Float.valueOf(servicePrice));
		tService.setTimesPerWeek(Integer.valueOf(serviceTimePerWeek));
		tService.setContent(serviceContent);
		tService.setReason("");
		tService.setAdvantage("");
		tService.setTeacher(teacher);
		teacher.settService(tService);

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
		getTeacherService().saveWithDetailInfo(teacher);
		setResMsg(MsgUtil.getSuccessMsg("insert teacher successfully"));
	}

}
