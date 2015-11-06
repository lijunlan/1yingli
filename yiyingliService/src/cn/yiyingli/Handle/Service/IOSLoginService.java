package cn.yiyingli.Handle.Service;

import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class IOSLoginService extends MsgService {

	private UserService userService;

	private UserMarkService userMarkService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
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
			setResMsg(MsgUtil.getErrorMsgByCode("12014"));
			return;
		}
		try {
			password = RSAUtil.decryptStrIOS(password, RSAUtil.RSAKEY_BASE_PATH);
			password = MD5Util.MD5(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("10001"));
			return;
		}
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("12015"));
			return;
		}
		if (password.equals(user.getPassword())) {
			String _UUID = "MOBILE_" + UUID.randomUUID().toString();
			getUserMarkService().save(String.valueOf(user.getId()), _UUID);
			if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
				setResMsg("{\"uid\":\"" + _UUID + "\",\"nickName\":\"" + user.getNickName() + "\",\"iconUrl\":\""
						+ (user.getIconUrl() != null ? user.getIconUrl() : "")
						+ "\",\"state\":\"success\",\"teacherId\":\"" + user.getTeacher().getId() + "\"}");
			} else {
				setResMsg("{\"uid\":\"" + _UUID + "\",\"nickName\":\"" + user.getNickName() + "\",\"iconUrl\":\""
						+ (user.getIconUrl() != null ? user.getIconUrl() : "") + "\",\"state\":\"success\"}");
			}
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("12016"));
		}
	}

}
