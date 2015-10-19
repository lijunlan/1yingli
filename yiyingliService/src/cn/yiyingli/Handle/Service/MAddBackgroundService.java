package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.MsgUtil;

public class MAddBackgroundService extends MsgService{

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
		return getData().containsKey("url")&&getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		Background bg=new Background();
		String url = (String) getData().get("url");
		bg.setUrl(url);
		getBackgroundService().save(bg);
		SuperMap toSend = MsgUtil.getSuccessMap();
		setResMsg(toSend.put("url", url).finishByJson());
	}

}
