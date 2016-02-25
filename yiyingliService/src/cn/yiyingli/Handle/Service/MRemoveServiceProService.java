package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class MRemoveServiceProService extends MMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42002"));
			return;
		}
		servicePro.setRemove(true);
		getServiceProService().updateAndPlusNumber(servicePro, true, true);
		setResMsg(MsgUtil.getSuccessMsg("remove servicePro successfully"));
	}

}
