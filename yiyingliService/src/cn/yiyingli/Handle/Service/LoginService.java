package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.LoginMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class LoginService extends LoginMsgService {

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
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("username is not existed"));
			return;
		}
		if (password.equals(user.getPassword())) {
			returnUser(user);
		} else {
			setResMsg(MsgUtil.getErrorMsg("password is not accurate"));
		}
	}

}
