package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.User;

public class ExUser {

	public static void assembleUserInfo(User user, SuperMap map) {
		map.put("address", user.getAddress());
		map.put("nickName", user.getNickName());
		map.put("name", user.getName());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		map.put("resume", user.getResume());
		map.put("mile", user.getMile());
	}

}
