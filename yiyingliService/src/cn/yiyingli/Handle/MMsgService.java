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
	public boolean validate() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("34001"));
			return false;
		}
		setManager(manager);
		if (!checkRoot()) {
			setResMsg(MsgUtil.getErrorMsgByCode("34002"));
			return false;
		}
		return true;
	}

	public boolean checkRoot() {
		String method = getData().getString("method");
		if (method.contains("Order") || method.contains("order") || method.contains("voucher")
				|| method.contains("Voucher") || method.contains("Activity") || method.contains("activity")
				|| method.contains("Distributor") || method.contains("distributor") || method.contains("reward")
				|| method.contains("Reward")) {
			if (manager.getRoot().shortValue() > 700) {
				if (method.contains("Passage") || method.contains("passage")) {
					if (manager.getRoot().shortValue() > 710) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
