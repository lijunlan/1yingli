package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CheckNoService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class RegisterService extends MsgService {

	private UserService userService;

	private CheckNoService checkNoService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CheckNoService getCheckNoService() {
		return checkNoService;
	}

	public void setCheckNoService(CheckNoService checkNoService) {
		this.checkNoService = checkNoService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("username") && getData().containsKey("password")
				&& getData().containsKey("checkNo") && getData().containsKey("nickName");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		String checkNo = (String) getData().get("checkNo");
		String nickName = (String) getData().get("nickName");
		if (nickName.length() > 7) {
			nickName = nickName.substring(0, 7);
		}
		if (!(CheckUtil.checkMobileNumber(username) || CheckUtil.checkEmail(username)) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsg("username or password is wrong"));
			return;
		}

		CheckNo no = getCheckNoService().query(username);
		long time = Calendar.getInstance().getTimeInMillis();
		if (no == null) {
			setResMsg(MsgUtil.getErrorMsg("this phone or email have no checkNo"));
			return;
		}
		if (!(no.getCheckNo().equals(checkNo))) {
			setResMsg(MsgUtil.getErrorMsg("checkNo is wrong"));
			return;
		} else if (time > Long.valueOf(no.getEndTime())) {
			setResMsg(MsgUtil.getErrorMsg("checkNo is overdue"));
			getCheckNoService().remove(no);
			return;
		} else {
			getCheckNoService().remove(no);
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
		user.setOrderNumber(0L);
		user.setUsername(username);
		user.setNickName(nickName);
		user.setReceiveCommentNumber(0L);
		user.setSendCommentNumber(0L);
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
