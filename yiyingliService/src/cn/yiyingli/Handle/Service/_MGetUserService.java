package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Service.UserService;

public class _MGetUserService extends MMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

	}

}
