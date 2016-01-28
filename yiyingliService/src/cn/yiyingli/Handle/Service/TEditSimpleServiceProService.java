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
				&& getData().containsKey("pricetemp") && getData().containsKey("onshow");

	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		PServiceProUtil.editPriceByTeacher(Float.valueOf(getData().getString("price")),
				Float.valueOf(getData().getString("numeral")), getData().getInt("count"),
				getData().getString("quantifier"), getData().getBoolean("onsale"),
				Float.valueOf(getData().getString("pricetemp")), getData().getBoolean("onshow"), servicePro);
		getServiceProService().update(servicePro);
		setResMsg(MsgUtil.getSuccessMsg("edit simple servicePro successfully"));
	}

}
