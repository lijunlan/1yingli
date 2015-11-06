package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.ManagerService;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class MLoginService extends MMsgService {

	private ManagerService managerService;

	public ManagerService getManagerService() {
		return managerService;
	}

	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("username") && getData().containsKey("password");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		if ("".equals(username) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("32010"));
			return;
		}
		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
			password = MD5Util.MD5(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("30001"));
			return;
		}
		Manager manager = getManagerService().query(username);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32011"));
			return;
		}
		if (password.equals(manager.getPassword())) {
			String _UUID = UUID.randomUUID().toString();
			getManagerMarkService().save(String.valueOf(manager.getId()), _UUID);
			manager.setLastLoginTime(Calendar.getInstance().getTimeInMillis() + "");
			getManagerService().update(manager);
			setResMsg("{\"mid\":\"" + _UUID + "\",\"state\":\"success\",\"mname\":\"" + manager.getName() + "\"}");
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("32012"));
		}
	}

}
