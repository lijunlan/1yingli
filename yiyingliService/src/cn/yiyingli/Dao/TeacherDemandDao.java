package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.TeacherDemand;

public interface TeacherDemandDao {

	void save(TeacherDemand teacherDemand);

	void remove(TeacherDemand teacherDemand);

	void remove(long id);

	void update(TeacherDemand teacherDemand);

	TeacherDemand query(long id);

	List<TeacherDemand> queryList();

	List<TeacherDemand> queryListByTime(int page, int pageSize);
}
