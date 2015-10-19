package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.WorkExperienceDao;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.WorkExperienceService;

public class WorkExperienceServiceImpl implements WorkExperienceService {

	private WorkExperienceDao workExperienceDao;

	public WorkExperienceDao getWorkExperienceDao() {
		return workExperienceDao;
	}

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}

	@Override
	public void save(WorkExperience workExperience) {
		getWorkExperienceDao().save(workExperience);
	}

	@Override
	public Long saveAndReturnId(WorkExperience workExperience) {
		return getWorkExperienceDao().saveAndReturnId(workExperience);
	}

	@Override
	public void remove(WorkExperience workExperience) {
		getWorkExperienceDao().remove(workExperience);
	}

	@Override
	public void remove(long id) {
		getWorkExperienceDao().remove(id);
	}

	@Override
	public void removeByTeacherId(long teacherId) {
		getWorkExperienceDao().removeByTeacherId(teacherId);
	}

	@Override
	public void update(WorkExperience workExperience) {
		getWorkExperienceDao().update(workExperience);
	}

	@Override
	public WorkExperience query(long id, boolean lazy) {
		return getWorkExperienceDao().query(id, lazy);
	}

	@Override
	public List<WorkExperience> queryListByTeahcerId(long teacherId, boolean lazy) {
		return getWorkExperienceDao().queryListByTeahcerId(teacherId, lazy);
	}

}
