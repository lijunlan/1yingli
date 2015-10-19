package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.StudyExperienceService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeStudyExperienceService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private StudyExperienceService studyExperienceService;

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

	public StudyExperienceService getStudyExperienceService() {
		return studyExperienceService;
	}

	public void setStudyExperienceService(StudyExperienceService studyExperienceService) {
		this.studyExperienceService = studyExperienceService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("studyExperience") && getData().containsKey("teacherId")
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
		List<Object> studyExperiences = (List<Object>) getData().get("studyExperience");
		teacher.getStudyExperiences().clear();
		for (Object se : studyExperiences) {
			StudyExperience studyExperience = new StudyExperience();
			studyExperience.setSchoolName((String) ((Map<String, Object>) se).get("schoolName"));
			studyExperience.setDescription((String) ((Map<String, Object>) se).get("description"));
			studyExperience.setEndTime((String) ((Map<String, Object>) se).get("endTime"));
			studyExperience.setStartTime((String) ((Map<String, Object>) se).get("startTime"));
			studyExperience.setDegree((String) ((Map<String, Object>) se).get("degree"));
			studyExperience.setMajor((String) ((Map<String, Object>) se).get("major"));
			studyExperience.setOwnTeacher(teacher);
			teacher.getStudyExperiences().add(studyExperience);
		}
		getTeacherService().updateStudyExp(teacher);
		setResMsg(MsgUtil.getSuccessMsg("studyExp has changed"));
	}

}
