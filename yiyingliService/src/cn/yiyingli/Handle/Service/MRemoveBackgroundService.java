package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Util.MsgUtil;

public class MRemoveBackgroundService extends MMsgService {

	private BackgroundService backgroundService;

	public BackgroundService getBackgroundService() {
		return backgroundService;
	}

	public void setBackgroundService(BackgroundService backgroundService) {
		this.backgroundService = backgroundService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("id");
	}

	@Override
	public void doit() {
		long id = Long.parseLong((String) getData().get("id"));
		Background bg = getBackgroundService().query(id);
		if (bg == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32013"));
			return;
		}
		getBackgroundService().remove(bg);
		SuperMap toSend = MsgUtil.getSuccessMap();
		setResMsg(toSend.put("data", "picture is removed. id=" + id).finishByJson());
	}

}
