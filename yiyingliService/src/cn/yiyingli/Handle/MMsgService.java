package cn.yiyingli.Handle;

import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.MsgUtil;

public abstract class MMsgService extends MsgService {

	private ManagerMarkService managerMarkService;

	private Manager manager;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	protected Manager getManager() {
		return manager;
	}

	private void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("34001"));
			return;
		}
		setManager(manager);
	}
}
