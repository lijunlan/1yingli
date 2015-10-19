package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.StudyExperienceDao;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Service.StudyExperienceService;

public class StudyExperienceServiceImpl implements StudyExperienceService {

	private StudyExperienceDao studyExperienceDao;

	public StudyExperienceDao getStudyExperienceDao() {
		return studyExperienceDao;
	}

	public void setStudyExperienceDao(StudyExperienceDao studyExperienceDao) {
		this.studyExperienceDao = studyExperienceDao;
	}

	@Override
	public void save(StudyExperience studyExperience) {
		getStudyExperienceDao().save(studyExperience);
	}

	@Override
	public Long saveAndReturnId(StudyExperience studyExperience) {
		return getStudyExperienceDao().saveAndReturnId(studyExperience);
	}

	@Override
	public void remove(StudyExperience studyExperience) {
		getStudyExperienceDao().remove(studyExperience);
	}

	@Override
	public void remove(long id) {
		getStudyExperienceDao().remove(id);
	}

	@Override
	public void removeByTeacherId(long teacherId) {
		getStudyExperienceDao().removeByTeacherId(teacherId);
	}

	@Override
	public void update(StudyExperience studyExperience) {
		getStudyExperienceDao().update(studyExperience);
	}

	@Override
	public StudyExperience query(long id, boolean lazy) {
		return getStudyExperienceDao().query(id, lazy);
	}

	@Override
	public List<StudyExperience> queryListByTeahcerId(long teacherId, boolean lazy) {
		return getStudyExperienceDao().queryListByTeahcerId(teacherId, lazy);
	}

}
