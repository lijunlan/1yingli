package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MChangeUserNameService extends MMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("oldusername") && getData().containsKey("newusername");

	}

	@Override
	public void doit() {
		String oldusername = getData().getString("oldusername");
		String newusername = getData().getString("newusername");
		User user = getUserService().query(oldusername, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode(""));
			return;
		}
		user.setUsername(newusername);
		getUserService().updateUsername(user);
		setResMsg(MsgUtil.getSuccessMsg("change username successfully"));
	}

}
