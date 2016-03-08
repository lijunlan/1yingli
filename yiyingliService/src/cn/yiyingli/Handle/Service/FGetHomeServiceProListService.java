package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class FGetHomeServiceProListService extends MsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	protected boolean checkData() {
		return true;
	}

	@Override
	public void doit() {
		List<ServicePro> servicePros = getServiceProService().queryListByActivity(PagesService.KEY_HOME_SERVICEPRO, 0,
				ServiceProService.HOMEPAGE_PAGE_SIZE);
		ExList exservicePros = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleSimpleServiceProForUser(servicePro, map);
			exservicePros.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", exservicePros).finishByJson());
	}

}
