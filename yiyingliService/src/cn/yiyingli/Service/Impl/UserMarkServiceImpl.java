package cn.yiyingli.Service.Impl;

import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Dao.UserMarkDao;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserMark;
import cn.yiyingli.Service.UserMarkService;

public class UserMarkServiceImpl implements UserMarkService {

	private UserMarkDao userMarkDao;

	private UserDao userDao;

	public UserMarkDao getUserMarkDao() {
		return userMarkDao;
	}

	public void setUserMarkDao(UserMarkDao userMarkDao) {
		this.userMarkDao = userMarkDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(UserMark userMark) {
		getUserMarkDao().save(userMark);
	}

	@Override
	public void save(String userId, String UUID) {
		User user = getUserDao().query(Long.valueOf(userId), false);
		UserMark um = getUserMarkDao().queryUUID(Long.valueOf(userId));
		if (um == null) {
			UserMark userMark = new UserMark();
			userMark.setUser(user);
			userMark.setUuid(UUID);
			save(userMark);
		} else {
			getUserMarkDao().update(userId, UUID);
		}
	}

	@Override
	public void remove(UserMark userMark) {
		getUserMarkDao().remove(userMark);

	}

	@Override
	public void remove(String UUID) {
		getUserMarkDao().remove(UUID);
	}

	@Override
	public UserMark query(String UUID) {
		return getUserMarkDao().query(UUID);
	}

	@Override
	public User queryUser(String UUID) {
		User user = getUserMarkDao().queryUser(UUID);
		return user;
	}

}
