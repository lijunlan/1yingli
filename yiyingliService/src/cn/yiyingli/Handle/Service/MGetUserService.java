package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.UserService;

public class MGetUserService extends MsgService {

	private UserService userService;

	private ManagerMarkService managerMarkService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
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
