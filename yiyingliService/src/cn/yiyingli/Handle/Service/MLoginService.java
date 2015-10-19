package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.ManagerService;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class MLoginService extends MsgService {

	private ManagerService managerService;

	private ManagerMarkService managerMarkService;

	public ManagerService getManagerService() {
		return managerService;
	}

	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
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
			setResMsg(MsgUtil.getErrorMsg("username or password can not be null"));
			return;
		}
		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
			password = MD5Util.MD5(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("error"));
			return;
		}
		Manager manager = getManagerService().query(username);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("username is not exsited"));
			return;
		}
		if (password.equals(manager.getPassword())) {
			String _UUID = UUID.randomUUID().toString();
			getManagerMarkService().save(String.valueOf(manager.getId()), _UUID);
			manager.setLastLoginTime(Calendar.getInstance().getTimeInMillis() + "");
			getManagerService().update(manager);
			setResMsg("{\"mid\":\"" + _UUID + "\",\"state\":\"success\",\"mname\":\"" + manager.getName() + "\"}");
		} else {
			setResMsg(MsgUtil.getErrorMsg("password is not accurate"));
		}
	}

}
