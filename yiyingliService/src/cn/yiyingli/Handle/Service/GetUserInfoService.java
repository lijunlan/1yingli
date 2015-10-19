package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class GetUserInfoService extends MsgService {

	private UserMarkService UserMarkService;

	private UserService userService;

	public UserMarkService getUserMarkService() {
		return UserMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		UserMarkService = userMarkService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("address", user.getAddress());
		map.put("nickName", user.getNickName());
		map.put("name", user.getName());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		map.put("resume", user.getResume());
		setResMsg(map.finishByJson());
	}

}
