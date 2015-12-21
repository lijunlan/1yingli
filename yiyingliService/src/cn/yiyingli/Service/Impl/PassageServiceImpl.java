package cn.yiyingli.Service.Impl;

import java.util.Calendar;
import java.util.List;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserLikePassage;
import cn.yiyingli.Service.PassageService;

public class PassageServiceImpl implements PassageService {

	private PassageDao passageDao;

	private TeacherDao teacherDao;

	private UserDao userDao;

	public PassageDao getPassageDao() {
		return passageDao;
	}

	public void setPassageDao(PassageDao passageDao) {
		this.passageDao = passageDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Override
	public void save(Passage passage) {
		getPassageDao().saveAndCount(passage, passage.getOwnTeacher());
	}

	@Override
	public Long saveAndReturnId(Passage passage) {
		return getPassageDao().saveAndReturnId(passage);
	}

	@Override
	public void remove(Passage passage) {
		getPassageDao().remove(passage);
	}

	@Override
	public void remove(long id) {
		getPassageDao().remove(id);
	}

	@Override
	public void update(Passage passage, boolean stateChange) {
		if (stateChange) {
			getPassageDao().updateAndCount(passage, passage.getOwnTeacher());
		} else {
			getPassageDao().update(passage);
		}
	}

	@Override
	public boolean updateUserLike(Passage passage, User user) {
		if (getPassageDao().queryCheckLikeUser(passage.getId(), user.getId())) {
			return false;
		} else {
			UserLikePassage userLikePassage = new UserLikePassage();
			userLikePassage.setPassage(passage);
			userLikePassage.setUser(user);
			userLikePassage.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			getUserDao().updateLikePassage(userLikePassage);
			return true;
		}
	}

	@Override
	public Passage query(long id) {
		return getPassageDao().query(id);
	}

	@Override
	public Passage queryWithTeacherById(long id) {
		return getPassageDao().queryWithTeacherById(id);
	}

	@Override
	public Passage queryByUser(long id) {
		return getPassageDao().queryByUser(id);
	}

	@Override
	public Passage queryByUserWithTeacher(long id) {
		return getPassageDao().queryByUserWithTeacher(id);
	}

	@Override
	public List<Passage> queryList(int page, int pageSize) {
		return getPassageDao().queryList(page, pageSize);
	}

	@Override
	public List<Passage> queryList(int page) {
		return getPassageDao().queryList(page, PAGE_SIZE_MANAGER);
	}

	@Override
	public List<Passage> queryListByState(int page, int pageSize, short state) {
		return getPassageDao().queryListByState(page, pageSize, state);
	}

	@Override
	public List<Passage> queryListByState(int page, short state) {
		return getPassageDao().queryListByState(page, PAGE_SIZE_MANAGER, state);
	}

	@Override
	public List<Passage> queryListByShow(int page, int pageSize, boolean show) {
		return getPassageDao().queryListByShow(page, pageSize, show);
	}

	@Override
	public List<Passage> queryListByShow(int page, boolean show) {
		return getPassageDao().queryListByShow(page, PAGE_SIZE_MANAGER, show);
	}

	@Override
	public List<Passage> queryListByTeacherAndState(int page, int pageSize, long teacherId, short state) {
		return getPassageDao().queryListByTeacherAndState(page, pageSize, teacherId, state);
	}

	@Override
	public List<Passage> queryListByTeacherAndState(int page, long teacherId, short state) {
		return getPassageDao().queryListByTeacherAndState(page, PAGE_SIZE, teacherId, state);
	}

}
