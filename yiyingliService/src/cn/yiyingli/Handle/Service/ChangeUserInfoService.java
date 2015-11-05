package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class ChangeUserInfoService extends UMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("nickName") && getData().containsKey("name")
				&& getData().containsKey("address") && getData().containsKey("resume");
	}

	@Override
	public void doit() {
		super.doit();
		User user = getUser();
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
