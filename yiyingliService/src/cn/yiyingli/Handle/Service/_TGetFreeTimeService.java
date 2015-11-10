package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.FreeTimeService;
import cn.yiyingli.Service.UserMarkService;

public class _TGetFreeTimeService extends MsgService {

	private FreeTimeService freeTimeService;

	private UserMarkService userMarkService;

	public FreeTimeService getFreeTimeService() {
		return freeTimeService;
	}

	public void setFreeTimeService(FreeTimeService freeTimeService) {
		this.freeTimeService = freeTimeService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
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
