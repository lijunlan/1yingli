package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.WorkExperience;

public interface WorkExperienceService {

	void save(WorkExperience workExperience);

	Long saveAndReturnId(WorkExperience workExperience);

	void remove(WorkExperience workExperience);

	void remove(long id);

	void removeByTeacherId(long teacherId);

	void update(WorkExperience workExperience);

	WorkExperience query(long id, boolean lazy);

	List<WorkExperience> queryListByTeahcerId(long teacherId, boolean lazy);

}
