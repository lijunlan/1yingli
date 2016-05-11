package cn.yiyingli.Service;

import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserMark;

public interface UserMarkService {

	void save(UserMark userMark);

	void save(String userId, String UUID);

	void remove(UserMark userMark);

	void remove(String UUID);

	UserMark queryUUID(long userId);

	UserMark query(String UUID);

	User queryUser(String UUID);
}
