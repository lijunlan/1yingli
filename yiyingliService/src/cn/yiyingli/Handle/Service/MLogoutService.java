package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.MsgUtil;

public class MLogoutService extends MsgService {

	private ManagerMarkService managerMarkService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String UUID = (String) getData().get("mid");
		getManagerMarkService().remove(UUID);
		setResMsg(MsgUtil.getSuccessMsg("logout successfully"));
	}
}
