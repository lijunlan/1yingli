package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TSMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Util.MsgUtil;

public class TGetServiceProService extends TSMsgService {

	@Override
	public void doit() {
		ServicePro servicePro = getServicePro();
		SuperMap map = MsgUtil.getSuccessMap();
		ExServicePro.assembleDetailServiceForTeacher(servicePro, map);
		setResMsg(map.finishByJson());
	}

}
