package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.StudyExperience;

public interface StudyExperienceService {

	void save(StudyExperience studyExperience);

	Long saveAndReturnId(StudyExperience studyExperience);

	void remove(StudyExperience studyExperience);

	void remove(long id);

	void removeByTeacherId(long teacherId);

	void update(StudyExperience studyExperience);

	StudyExperience query(long id, boolean lazy);

	List<StudyExperience> queryListByTeahcerId(long teacherId, boolean lazy);

}
