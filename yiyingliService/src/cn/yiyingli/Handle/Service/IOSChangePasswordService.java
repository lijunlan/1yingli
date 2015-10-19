package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class IOSChangePasswordService extends MsgService {

	private UserMarkService userMarkService;

	private UserService userService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("password") && getData().containsKey("oldpassword")
				&& getData().containsKey("uid");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		String password = (String) getData().get("password");
		String oldpassword = (String) getData().get("oldpassword");
		if ("".equals(password) || "".equals(oldpassword)) {
			setResMsg(MsgUtil.getErrorMsg("oldpassword or newpassword can not be null"));
			return;
		}
		try {
			password = RSAUtil.decryptStrIOS(password, RSAUtil.RSAKEY_BASE_PATH);
			if(!CheckUtil.checkPassword(password)){
				setResMsg(MsgUtil.getErrorMsg("BAD PASSWROD!"));
				return;
			}
			password = MD5Util.MD5(password);
			oldpassword = RSAUtil.decryptStrIOS(oldpassword, RSAUtil.RSAKEY_BASE_PATH);
			oldpassword = MD5Util.MD5(oldpassword);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("error"));
			return;
		}
		if (oldpassword.equals(user.getPassword())) {
			user.setPassword(password);
			getUserService().update(user);
			setResMsg(MsgUtil.getSuccessMsg("password has changed"));
		} else {
			setResMsg(MsgUtil.getErrorMsg("old password is wrong"));
		}
	}
}