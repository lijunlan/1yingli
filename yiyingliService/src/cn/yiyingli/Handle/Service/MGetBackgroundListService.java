package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Service.BackgroundService;
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
		// || getData().containsKey("page");
		return super.checkData();
	}

	@Override
	public void doit() {
		List<Background> bgList = null;
		if (getData().containsKey("page")) {
			int page;
			try {
				page = getData().getInt("page");
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsgByCode("32009"));
				return;
			}
			bgList = getBackgroundService().queryList(page, 10);
		}else{
			bgList = getBackgroundService().queryList();
		}
		ExList send = new ExArrayList();
		SuperMap toSend = MsgUtil.getSuccessMap();
		for (Background bg : bgList) {
			SuperMap tmp = new SuperMap();
			tmp.put("url", bg.getUrl());
			tmp.put("id", bg.getId());
			send.add(tmp.finish());
		}
		setResMsg(toSend.put("data", send).finishByJson());
	}

}
