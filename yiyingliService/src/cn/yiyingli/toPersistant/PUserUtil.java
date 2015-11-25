package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;

public class PUserUtil {

	public static User assembleUserFromWX(String username, String password, String nickName, String icon,
			String address) {
		User user = assembleUser(username, password, nickName, icon, address, null);
		user.setWechatNo(username);
		return user;
	}

	public static User assembleUserFromWB(String username, String password, String nickName, String icon,
			String address) {
		User user = assembleUser(username, password, nickName, icon, address, null);
		user.setWeiboNo(username);
		return user;
	}

	public static User assembleUser(String username, String password, String nickName, String icon, String address,
			Distributor distributor) {
		User user = new User();
		user.setLikeTeacherNumber(0L);
		user.setOrderNumber(0L);
		user.setUsername(username);
		user.setNickName(nickName);
		user.setReceiveCommentNumber(0L);
		user.setSendCommentNumber(0L);
		user.setMile(0L);
		user.setIconUrl(icon);
		if (distributor != null)
			user.setDistributor(distributor);
		user.setAddress(address);
		user.setPassword(password);
		user.setCreateTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		user.setTeacherState(UserService.TEACHER_STATE_OFF_SHORT);
		user.setForbid(false);
		return user;
	}
}
