package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class FGetServiceProService extends MsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().queryByUser(getData().getLong("serviceProId"));
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22008"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExServicePro.assembleDetailServiceForUser(servicePro, map);
		setResMsg(map.finishByJson());
	}

}
