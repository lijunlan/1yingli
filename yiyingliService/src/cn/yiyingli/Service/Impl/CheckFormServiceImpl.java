package cn.yiyingli.Service.Impl;

import java.util.Calendar;
import java.util.List;

import cn.yiyingli.Dao.CheckFormDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.CheckFormService;

public class CheckFormServiceImpl implements CheckFormService {

	private CheckFormDao checkFormDao;

	private TeacherDao teacherDao;

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public CheckFormDao getCheckFormDao() {
		return checkFormDao;
	}

	public void setCheckFormDao(CheckFormDao checkFormDao) {
		this.checkFormDao = checkFormDao;
	}

	@Override
	public void save(CheckForm checkForm) {
		getTeacherDao().update(checkForm.getTeacher());
		getCheckFormDao().save(checkForm);
	}

	@Override
	public Long saveAndReturnId(CheckForm checkForm) {
		return getCheckFormDao().saveAndReturnId(checkForm);
	}

	@Override
	public void remove(CheckForm checkForm) {
		getCheckFormDao().remove(checkForm);
	}

	@Override
	public void remove(long id) {
		getCheckFormDao().remove(id);
	}

	@Override
	public void update(CheckForm checkForm) {
		getCheckFormDao().update(checkForm);
	}

	@Override
	public void finishCheck(long id, boolean accept, String description, Manager manager) {
		CheckForm checkForm = getCheckFormDao().query(id);
		if (accept) {
			checkForm.setState(CHECK_STATE_SUCCESS_SHORT);
		} else {
			checkForm.setState(CHECK_STATE_FAILED_SHORT);
		}
		checkForm.setCheckInfo(description);
		checkForm.setEndManager(manager);
		checkForm.setEndTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		getCheckFormDao().update(checkForm);
	}

	@Override
	public CheckForm query(long id) {
		return getCheckFormDao().query(id);
	}

	@Override
	public CheckForm query(String teacherId) {
		return getCheckFormDao().query(teacherId);
	}

	@Override
	public List<CheckForm> queryList() {
		return getCheckFormDao().queryList();
	}

}
