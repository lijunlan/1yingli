package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;
import cn.yiyingli.toPersistant.PUserUtil;

public class MRegisterUserService extends MMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("username") && getData().containsKey("password")
				&& getData().containsKey("nickName");
	}

	@Override
	public void doit() {
		super.doit();
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		String nickName = (String) getData().get("nickName");
		if (!(CheckUtil.checkMobileNumber(username) || CheckUtil.checkEmail(username)) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("12017"));
			return;
		}

		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("10001"));
			return;
		}
		if (!CheckUtil.checkPassword(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("12005"));
			return;
		}
		password = MD5Util.MD5(password);
		User user = PUserUtil.assembleUser(username, password, nickName, null, null, null);
		if (CheckUtil.checkMobileNumber(username)) {
			user.setPhone(username);
		} else {
			user.setEmail(username);
		}
		try {
			getUserService().save(user);
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("15003"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMsg("register successfully"));
	}

}
