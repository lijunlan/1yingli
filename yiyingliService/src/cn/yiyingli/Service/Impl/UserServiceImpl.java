package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private TeacherDao teacherDao;

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
	public void save(User user) throws Exception {
		User u = getUserDao().query(user.getUsername(), false);
		if (u == null) {
			getUserDao().save(user);
		} else {
			throw new Exception("phone or email has been registered");
		}
	}

	@Override
	public Long saveAndReturnId(User user) {
		return getUserDao().saveAndReturnId(user);
	}

	@Override
	public void remove(User user) {
		getUserDao().remove(user);
	}

	@Override
	public void remove(long id) {
		getUserDao().remove(id);
	}

	@Override
	public void update(User user) {
		getUserDao().update(user);
	}

	@Override
	public void updateWithTeacher(User user) {
		if (user.getTeacherState() == TEACHER_STATE_ON_SHORT) {
			Teacher teacher = getTeacherDao().queryByUserId(user.getId(), false);
			// teacher.setSex(user.getSex());
			teacher.setEmail(user.getEmail());
			teacher.setName(user.getName());
			teacher.setPhone(user.getPhone());
			teacher.setIconUrl(user.getIconUrl());
			getUserDao().merge(user);
			getTeacherDao().merge(teacher);
		} else {
			getUserDao().update(user);
		}
	}

	@Override
	public void changePassword(long userId, String oldpwd, String newpwd) throws Exception {
		User user = getUserDao().query(userId, false);
		if (user.getPassword().equals(oldpwd)) {
			user.setPassword(newpwd);
		} else {
			throw new Exception("old password is not accurate");
		}
	}

	@Override
	public User query(long id, boolean lazy) {
		return getUserDao().query(id, lazy);
	}

	@Override
	public User queryByNo(String no, boolean lazy) {
		return getUserDao().queryByNo(no, lazy);
	}

	@Override
	public User queryWithTeacher(long id, boolean lazy) {
		return getUserDao().queryWithTeacher(id, lazy);
	}

	@Override
	public User query(String username, boolean lazy) {
		return getUserDao().queryWithTeacher(username, lazy);
	}

	@Override
	public User queryWithWeibo(String weiboNo, boolean lazy) {
		return getUserDao().queryWithWeibo(weiboNo, lazy);
	}

	@Override
	public User queryWithWeixin(String weixinNo, boolean lazy) {
		return getUserDao().queryWithWeixin(weixinNo, lazy);
	}

	@Override
	public User queryWithTeacher(String username, boolean lazy) {
		return getUserDao().queryWithTeacher(username, lazy);
	}

	@Override
	public List<User> queryList(int page, int pageSize, boolean lazy) {
		return getUserDao().queryList(page, pageSize, lazy);
	}

	@Override
	public List<User> queryListByForbid(int page, int pageSize, boolean forbid, boolean lazy) {
		return getUserDao().queryListByForbid(page, pageSize, forbid, lazy);
	}

	@Override
	public List<User> queryListByValidate(int page, int pageSize, boolean validate, boolean lazy) {
		return getUserDao().queryListByValidate(page, pageSize, validate, lazy);
	}

	@Override
	public List<User> queryListByLevel(int page, int pageSize, short level, boolean lazy) {
		return getUserDao().queryListByLevel(page, pageSize, level, lazy);
	}

	@Override
	public List<User> queryListByTeacher(int page, int pageSize, short teacherState, boolean lazy) {
		return getUserDao().queryListByTeacher(page, pageSize, teacherState, lazy);
	}

	@Override
	public List<User> queryList(int page, boolean lazy) {
		return queryList(page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<User> queryListByForbid(int page, boolean forbid, boolean lazy) {
		return queryListByForbid(page, PAGE_SIZE_INT, forbid, lazy);
	}

	@Override
	public List<User> queryListByValidate(int page, boolean validate, boolean lazy) {
		return queryListByValidate(page, PAGE_SIZE_INT, validate, lazy);
	}

	@Override
	public List<User> queryListByLevel(int page, short level, boolean lazy) {
		return queryListByLevel(page, PAGE_SIZE_INT, level, lazy);
	}

	@Override
	public List<User> queryListByTeacher(int page, short teacherState, boolean lazy) {
		return queryListByTeacher(page, PAGE_SIZE_INT, teacherState, lazy);
	}

}
