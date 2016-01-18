package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class FGetServiceProListByKindService extends MsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("kind");
	}

	@Override
	public void doit() {
		List<ServicePro> servicePros = getServiceProService().queryListByKind(getData().getInt("kind"));
		ExList toSend = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleSimpleServiceProForUser(servicePro, map);
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}

}
