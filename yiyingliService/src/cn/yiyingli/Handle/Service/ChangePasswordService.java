package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class ChangePasswordService extends UMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("password") && getData().containsKey("oldpassword");
	}

	@Override
	public void doit() {
		User user = getUser();
		String password = (String) getData().get("password");
		String oldpassword = (String) getData().get("oldpassword");
		if ("".equals(password) || "".equals(oldpassword)) {
			setResMsg(MsgUtil.getErrorMsgByCode("12004"));
			return;
		}
		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
			if (!CheckUtil.checkPassword(password)) {
				setResMsg(MsgUtil.getErrorMsgByCode("12005"));
				return;
			}
			password = MD5Util.MD5(password);
			oldpassword = RSAUtil.decryptStr(oldpassword, RSAUtil.RSAKEY_BASE_PATH);
			oldpassword = MD5Util.MD5(oldpassword);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("10001"));
			return;
		}
		if (oldpassword.equals(user.getPassword())) {
			user.setPassword(password);
			getUserService().update(user);
			setResMsg(MsgUtil.getSuccessMsg("password has changed"));
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("12006"));
		}
	}

}
