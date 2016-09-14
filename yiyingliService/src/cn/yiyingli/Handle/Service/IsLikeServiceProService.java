package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class IsLikeServiceProService extends UMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	protected boolean checkData() {
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
		if (getServiceProService().queryCheckLikeUser(servicePro.getId(), user.getId())) {
			setResMsg(MsgUtil.getSuccessMap().put("likeServicePro", true).finishByJson());
		} else {
			setResMsg(MsgUtil.getSuccessMap().put("likeServicePro", false).finishByJson());
		}
	}

}
