package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;

public class TEditSimpleServiceProService extends TMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("serviceProId") && getData().containsKey("price")
				&& getData().containsKey("numeral") && getData().containsKey("count")
				&& getData().containsKey("quantifier") && getData().containsKey("onsale")
				&& getData().containsKey("pricetemp") && getData().containsKey("title")
				&& getData().containsKey("onshow");

	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		PServiceProUtil.editPriceByTeacher(price, numeral, count, quantifier, title, onsale, pricetemp, onshow,
				servicePro);
		getServiceProService().update(servicePro);
		setResMsg(MsgUtil.getSuccessMsg("edit simple servicePro successfully"));
	}

}
