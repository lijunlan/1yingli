package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;
import cn.yiyingli.Util.TimeTaskUtil;

public class LoginService extends MsgService {

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
			String _UUID = UUID.randomUUID().toString();
			getUserMarkService().save(String.valueOf(user.getId()), _UUID);
			user.setLastLoginTime("" + Calendar.getInstance().getTimeInMillis());
			getUserService().update(user);
			if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
				setResMsg("{\"uid\":\"" + _UUID + "\",\"nickName\":\"" + user.getNickName() + "\",\"iconUrl\":\""
						+ (user.getIconUrl() != null ? user.getIconUrl() : "") + "\",\"state\":\"success\",\"teacherId\":\""
						+ user.getTeacher().getId() + "\"}");
			} else {
				setResMsg("{\"uid\":\"" + _UUID + "\",\"nickName\":\"" + user.getNickName() + "\",\"iconUrl\":\""
						+ (user.getIconUrl() != null ? user.getIconUrl() : "") + "\",\"state\":\"success\"}");
			}
			TimeTaskUtil.sendTimeTask("remove", "userMark",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 12) + "", _UUID);
		} else {
			setResMsg(MsgUtil.getErrorMsg("password is not accurate"));
		}
	}
}
