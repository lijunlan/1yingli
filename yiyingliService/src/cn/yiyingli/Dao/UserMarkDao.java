package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserMark;

public interface UserMarkDao {

	void save(UserMark userMark);

	void remove(UserMark userMark);

	void remove(String UUID);

	void update(UserMark userMark);

	void update(String userId, String UUID);

	UserMark queryUUID(long userId);

	UserMark query(String UUID);

	User queryUser(String UUID);
}
