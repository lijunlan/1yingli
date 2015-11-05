package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.DMsgService;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Util.MsgUtil;

public class DGetInfoService extends DMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData();
	}

	@Override
	public void doit() {
		super.doit();
		Distributor distributor = getDistributor();
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("registerNumber", distributor.getRegisterNumber());
		map.put("dealNumber", distributor.getDealNumber());
		map.put("orderNumber", distributor.getOrderNumber());
		map.put("number", distributor.getNumber());
		setResMsg(map.finishByJson());
	}

}
