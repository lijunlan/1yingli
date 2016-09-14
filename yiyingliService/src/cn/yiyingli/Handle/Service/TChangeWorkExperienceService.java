package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.WorkExperienceService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeWorkExperienceService extends TMsgService {

	private WorkExperienceService workExperienceService;

	public WorkExperienceService getWorkExperienceService() {
		return workExperienceService;
	}

	public void setWorkExperienceService(WorkExperienceService workExperienceService) {
		this.workExperienceService = workExperienceService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("workExperience");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doit() {
		Teacher teacher = getTeacher();
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
		getTeacherService().updateWorkExp(teacher,false);
		setResMsg(MsgUtil.getSuccessMsg("workExp has changed"));
	}

}
