package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Util.MsgUtil;

public class MAddBackgroundService extends MMsgService {

	private BackgroundService backgroundService;

	public BackgroundService getBackgroundService() {
		return backgroundService;
	}

	public void setBackgroundService(BackgroundService backgroundService) {
		this.backgroundService = backgroundService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("url");
	}

	@Override
	public void doit() {
		super.doit();
		Background bg = new Background();
		String url = (String) getData().get("url");
		bg.setUrl(url);
		getBackgroundService().save(bg);
		SuperMap toSend = MsgUtil.getSuccessMap();
		setResMsg(toSend.put("url", url).finishByJson());
	}

}
