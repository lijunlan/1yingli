package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.TeacherDemand;

public interface TeacherDemandService {

	public static final int PAGE_SIZE = 10;

	void save(TeacherDemand teacherDemand);

	void remove(TeacherDemand teacherDemand);

	void remove(long id);

	void update(TeacherDemand teacherDemand);

	TeacherDemand query(long id);

	List<TeacherDemand> queryList();

	List<TeacherDemand> queryListByTime(int page, int pageSize);

	List<TeacherDemand> queryListByTime(int page);
}
