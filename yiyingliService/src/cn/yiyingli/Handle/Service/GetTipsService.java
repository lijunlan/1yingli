package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;

public class GetTipsService extends MsgService {

	private TipService tipService;

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return true;
	}

	@Override
	public void doit() {
		List<Tip> tips = getTipService().queryNormalList();
		ExList toSend = new ExArrayList();
		for (Tip t : tips) {
			SuperMap map = new SuperMap();
			map.put("tid", t.getId());
			map.put("name", t.getName());
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("tips", toSend).finishByJson());
	}

}
