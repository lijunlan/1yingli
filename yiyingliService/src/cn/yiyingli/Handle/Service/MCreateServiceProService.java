package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import net.sf.json.JSONObject;

public class MCreateServiceProService extends MMsgService {

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
		return super.checkData() && getData().containsKey("username") && getData().containsKey("servicePro");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32011"));
			return;
		}
		Teacher teacher = user.getTeacher();
		ServicePro servicePro = new ServicePro();
		JSONObject jsonServicePro = getData().getJSONObject("servicePro");
		PServiceProUtil.assembleWithTeacherByManager(teacher, jsonServicePro, servicePro);
		getServiceProService().saveAndPlusNumber(servicePro, true, true);
		setResMsg(MsgUtil.getSuccessMsg("create ServicePro successfully"));
	}

}
