package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetBackgroundListService extends MsgService{

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
		return getData().containsKey("mid")&&getData().containsKey("page");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		int page;
		try{
			page=Integer.parseInt((String)getData().get("page"));
		}
		catch(Exception e){
			setResMsg(MsgUtil.getErrorMsg("page is wrong"));
			return;
		}
		List<Background> bgList=getBackgroundService().queryList(page, 10);
		List<String> send=new ArrayList<>();
		SuperMap toSend = MsgUtil.getSuccessMap();
		for(Background bg:bgList){
			SuperMap tmp=new SuperMap();
			tmp.put("url", bg.getUrl());
			tmp.put("id", bg.getId());
			send.add(tmp.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(send)).finishByJson());
	}

}
