package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.WorkExperience;

public interface WorkExperienceDao {

	void save(WorkExperience workExperience);

	Long saveAndReturnId(WorkExperience workExperience);

	void remove(WorkExperience workExperience);

	void remove(long id);
	
	void removeByTeacherId(long teacherId);

	void update(WorkExperience workExperience);

	void updateFromSql(String sql);

	WorkExperience query(long id, boolean lazy);

	List<WorkExperience> queryListByTeahcerId(long teacherId, boolean lazy);

}
