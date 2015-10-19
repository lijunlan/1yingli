package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.MsgUtil;

public class MRemoveBackgroundService extends MsgService {

	private ManagerMarkService managerMarkService;

	private BackgroundService backgroundService;

	public BackgroundService getBackgroundService() {
		return backgroundService;
	}

	public void setBackgroundService(BackgroundService backgroundService) {
		this.backgroundService = backgroundService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid")&&getData().containsKey("id");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		long id=Long.parseLong((String)getData().get("id"));
		Background bg=getBackgroundService().query(id);
		if(bg==null){
			setResMsg(MsgUtil.getErrorMsg("id is not existed"));
			return;
		}
		getBackgroundService().remove(bg);
		SuperMap toSend = MsgUtil.getSuccessMap();
		setResMsg(toSend.put("data", "picture is removed. id="+id).finishByJson());
	}

}
