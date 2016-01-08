package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class MGetServiceProService extends MMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		if(servicePro==null){
			setResMsg(MsgUtil.getErrorMsgByCode("22008"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExServicePro.assembleDetailServiceForManager(servicePro, map);
		setResMsg(map.finishByJson());
	}

}
