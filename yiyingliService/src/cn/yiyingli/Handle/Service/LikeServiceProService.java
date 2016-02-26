package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class LikeServiceProService extends UMsgService {

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
		User user = getUser();
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"),false);
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22008"));
			return;
		}
		getServiceProService().updateUserLike(servicePro, user);
		setResMsg(MsgUtil.getSuccessMsg("liked"));

	}

}
