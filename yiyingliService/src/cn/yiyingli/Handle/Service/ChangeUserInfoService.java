package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class ChangeUserInfoService extends MsgService {

	private UserService userService;

	private UserMarkService userMarkService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("nickName") && getData().containsKey("name")
				&& getData().containsKey("address") && getData().containsKey("resume");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		String name = (String) getData().get("name");
		String address = (String) getData().get("address");
		String nickName = (String) getData().get("nickName");
		String resume = (String) getData().get("resume");

		user.setAddress(address);
		user.setName(name);
		user.setNickName(nickName);
		user.setResume(resume);

		getUserService().updateWithTeacher(user);
		setResMsg(MsgUtil.getSuccessMsg("user info has changed"));

	}

}
