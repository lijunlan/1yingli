package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.ULoginMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

import java.util.List;

public class LoginService extends ULoginMsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("username") && getData().containsKey("password");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		if ("".equals(username) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("12014"));
			return;
		}
		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
			password = MD5Util.MD5(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("10001"));
			return;
		}
		List<User> users = getUserService().queryListWithTeacher(username, false);
		if(CheckUtil.checkGlobleMobileNumber(username)) {
			String addusername = username.replaceAll("-","");
			List<User> addUser = getUserService().queryListWithTeacher(addusername,false);
			users.addAll(addUser);
			if(username.split("-").length > 1) {
				String shortUsername = username.split("-")[1];
				addUser = getUserService().queryListWithTeacher(shortUsername,false);
				users.addAll(addUser);
			}
		}
		if (users.size() == 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("12015"));
			return;
		}
		User user = users.get(0);
		if(users.size() > 1) {
			getUserService().mergeUserWithUserList(user, users);
		}
		if (password.equals(user.getPassword())) {
			returnUser(user, false);
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("12016"));
		}
	}

}
