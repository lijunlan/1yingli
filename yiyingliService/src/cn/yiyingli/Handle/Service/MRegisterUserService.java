package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class MRegisterUserService extends MsgService {

	private UserService userService;

	private ManagerMarkService managerMarkService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("username") && getData().containsKey("password")
				&& getData().containsKey("nickName") && getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		String nickName = (String) getData().get("nickName");
		if (!(CheckUtil.checkMobileNumber(username) || CheckUtil.checkEmail(username)) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsg("username or password is wrong"));
			return;
		}

		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("error"));
			return;
		}
		if (!CheckUtil.checkPassword(password)) {
			setResMsg(MsgUtil.getErrorMsg("BAD PASSWROD!"));
			return;
		}
		password = MD5Util.MD5(password);
		User user = new User();
		user.setLikeTeacherNumber(0L);
		user.setUsername(username);
		user.setNickName(nickName);
		user.setReceiveCommentNumber(0L);
		user.setSendCommentNumber(0L);
		user.setOrderNumber(0L);
		if (CheckUtil.checkMobileNumber(username)) {
			user.setPhone(username);
		} else {
			user.setEmail(username);
		}
		user.setPassword(password);
		user.setCreateTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		user.setTeacherState(UserService.TEACHER_STATE_OFF_SHORT);
		user.setForbid(false);
		try {
			getUserService().save(user);
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsg("username is exsited"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMsg("register successfully"));
	}

}
