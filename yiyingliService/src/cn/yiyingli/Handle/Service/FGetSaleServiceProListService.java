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

public class FGetSaleServiceProListService extends MsgService {

	private ServiceProService serviceProService;

	private PagesService pagesService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("page");
	}

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		int p = 0;
		try {
			p = Integer.parseInt(page);
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("51001"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		List<ServicePro> servicePros = getServiceProService().queryListByActivity(PagesService.KEY_SALE_SERVICEPRO, p,
				ServiceProService.SALEPAGE_PAGE_SIZE);
		long sum = getPagesService().queryByKey(PagesService.KEY_SALE_SERVICEPRO).getServiceProCount();
		toSend.put("count", sum);
		ExList exservicePros = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleSimpleServiceProForUser(servicePro, map);
			exservicePros.add(map.finish());
		}
		if (exservicePros.size() == ServiceProService.SALEPAGE_PAGE_SIZE) {
			setResMsg(toSend.put("data", exservicePros).finishByJson());
		} else {
			setResMsg(toSend.put("data", exservicePros).put("page", "max").finishByJson());
		}
	}

}
