package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.StudyExperience;

public interface StudyExperienceDao {

	void save(StudyExperience studyExperience);

	Long saveAndReturnId(StudyExperience studyExperience);

	void remove(StudyExperience studyExperience);

	void remove(long id);
	
	void removeByTeacherId(long teacherId);

	void update(StudyExperience studyExperience);

	void updateFromSql(String sql);

	StudyExperience query(long id, boolean lazy);

	List<StudyExperience> queryListByTeahcerId(long teacherId, boolean lazy);

}
