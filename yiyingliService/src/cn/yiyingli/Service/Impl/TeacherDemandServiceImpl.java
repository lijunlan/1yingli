package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.TeacherDemandDao;
import cn.yiyingli.Persistant.TeacherDemand;
import cn.yiyingli.Service.TeacherDemandService;

public class TeacherDemandServiceImpl implements TeacherDemandService {

	private TeacherDemandDao teacherDemandDao;

	public TeacherDemandDao getTeacherDemandDao() {
		return teacherDemandDao;
	}

	public void setTeacherDemandDao(TeacherDemandDao teacherDemandDao) {
		this.teacherDemandDao = teacherDemandDao;
	}

	@Override
	public void save(TeacherDemand teacherDemand) {
		getTeacherDemandDao().save(teacherDemand);
	}

	@Override
	public void remove(TeacherDemand teacherDemand) {
		getTeacherDemandDao().remove(teacherDemand);
	}

	@Override
	public void remove(long id) {
		getTeacherDemandDao().remove(id);
	}

	@Override
	public void update(TeacherDemand teacherDemand) {
		getTeacherDemandDao().update(teacherDemand);
	}

	@Override
	public TeacherDemand query(long id) {
		return getTeacherDemandDao().query(id);
	}

	@Override
	public List<TeacherDemand> queryList() {
		return getTeacherDemandDao().queryList();
	}

	@Override
	public List<TeacherDemand> queryListByTime(int page, int pageSize) {
		return getTeacherDemandDao().queryListByTime(page, pageSize);
	}

	@Override
	public List<TeacherDemand> queryListByTime(int page) {
		return getTeacherDemandDao().queryListByTime(page, PAGE_SIZE);
	}

}
