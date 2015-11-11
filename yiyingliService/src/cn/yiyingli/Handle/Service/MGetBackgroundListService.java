package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetBackgroundListService extends MMsgService {

	private BackgroundService backgroundService;

	public BackgroundService getBackgroundService() {
		return backgroundService;
	}

	public void setBackgroundService(BackgroundService backgroundService) {
		this.backgroundService = backgroundService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page;
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		List<Background> bgList = getBackgroundService().queryList(page, 10);
		List<String> send = new ArrayList<>();
		SuperMap toSend = MsgUtil.getSuccessMap();
		for (Background bg : bgList) {
			SuperMap tmp = new SuperMap();
			tmp.put("url", bg.getUrl());
			tmp.put("id", bg.getId());
			send.add(tmp.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(send)).finishByJson());
	}

}
