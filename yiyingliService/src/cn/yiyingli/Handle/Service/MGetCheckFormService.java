package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.CheckFormService;
import cn.yiyingli.Service.ManagerMarkService;

public class MGetCheckFormService extends MsgService {

	private CheckFormService checkFormService;

	private ManagerMarkService managerMarkService;

	public CheckFormService getCheckFormService() {
		return checkFormService;
	}

	public void setCheckFormService(CheckFormService checkFormService) {
		this.checkFormService = checkFormService;
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
