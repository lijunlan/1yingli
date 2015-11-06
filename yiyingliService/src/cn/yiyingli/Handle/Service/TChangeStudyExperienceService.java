package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.StudyExperienceService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeStudyExperienceService extends TMsgService {

	private StudyExperienceService studyExperienceService;

	public StudyExperienceService getStudyExperienceService() {
		return studyExperienceService;
	}

	public void setStudyExperienceService(StudyExperienceService studyExperienceService) {
		this.studyExperienceService = studyExperienceService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("studyExperience");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doit() {
		Teacher teacher = getTeacher();
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
