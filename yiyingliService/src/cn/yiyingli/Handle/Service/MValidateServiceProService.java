package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class MValidateServiceProService extends MMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("serviceProId") && getData().containsKey("deal");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		boolean deal = getData().getBoolean("deal");
		boolean toBaidu = false;
		if (deal) {
			servicePro.setState(ServiceProService.STATE_OK);
			servicePro.setOnShow(true);
			toBaidu = true;
		} else {
			servicePro.setState(ServiceProService.STATE_FAILED);
			toBaidu = false;
		}
		getServiceProService().updateAndPlusNumber(servicePro, false, toBaidu);
		setResMsg(MsgUtil.getSuccessMsg("validate servicePro successfully"));
	}

}
