package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MEditUserMileService extends MMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("userId") && getData().containsKey("mile");
	}

	@Override
	public void doit() {
		long userId = Long.valueOf((String) getData().get("userId"));
		User user = getUserService().query(userId, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32014"));
			return;
		}
		long mile = Long.valueOf((String) getData().get("mile"));
		user.setMile(mile);
		setResMsg(MsgUtil.getSuccessMsg("change user's mile successfully"));
	}

}
