package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.WorkExperienceService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeWorkExperienceService extends MsgService {
	
	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private WorkExperienceService workExperienceService;

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

	public WorkExperienceService getWorkExperienceService() {
		return workExperienceService;
	}

	public void setWorkExperienceService(WorkExperienceService workExperienceService) {
		this.workExperienceService = workExperienceService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("workExperience") && getData().containsKey("teacherId")
				&& getData().containsKey("uid");
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
		Teacher teacher = getTeacherService().queryByUserId(user.getId(), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("you are not a teacher"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsg("uid don't match teacherId"));
			return;
		}
		List<Object> workExperiences = (List<Object>) getData().get("workExperience");
		teacher.getWorkExperiences().clear();
		for (Object we : workExperiences) {
			WorkExperience workExperience = new WorkExperience();
			workExperience.setCompanyName((String) ((Map<String, Object>) we).get("companyName"));
			workExperience.setDescription((String) ((Map<String, Object>) we).get("description"));
			workExperience.setEndTime((String) ((Map<String, Object>) we).get("endTime"));
			workExperience.setStartTime((String) ((Map<String, Object>) we).get("startTime"));
			workExperience.setPosition((String) ((Map<String, Object>) we).get("position"));
			workExperience.setOwnTeacher(teacher);
			teacher.getWorkExperiences().add(workExperience);
		}
		getTeacherService().updateWorkExp(teacher);
		setResMsg(MsgUtil.getSuccessMsg("workExp has changed"));
	}

}
