package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MGetServiceProListService extends MMsgService {

	private ServiceProService serviceProService;

	private UserService userService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("page") || getData().containsKey("username");
	}

	@Override
	public void doit() {
		int page = getData().getInt("page");
		List<ServicePro> servicePros = null;
		if (getData().containsKey("username")) {
			String username = (String) getData().get("username");
			User user = getUserService().queryWithTeacher(username, false);
			if (user == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("14001"));
				return;
			}
			servicePros = getServiceProService().queryListByTeacherId(user.getTeacher().getId(), page);
		} else {
			servicePros = getServiceProService().queryList(page);
		}
		ExList toSend = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleDetailServiceForManager(servicePro, map);
			toSend.add(map.finish());
		}
		SuperMap send = MsgUtil.getSuccessMap().put("data", toSend);
		setResMsg(send.finishByJson());
	}

}
