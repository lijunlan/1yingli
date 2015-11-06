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

public class IOSRegisterService extends MsgService {

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
		if (!(CheckUtil.checkMobileNumber(username) || CheckUtil.checkEmail(username)) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("12017"));
			return;
		}

		// TODO check No
		CheckNo no = getCheckNoService().query(username);
		long time = Calendar.getInstance().getTimeInMillis();

		if (no == null || !(no.getCheckNo().equals(checkNo))) {
			setResMsg(MsgUtil.getErrorMsgByCode("12001"));
			return;
		} else if (time > Long.valueOf(no.getEndTime())) {
			setResMsg(MsgUtil.getErrorMsgByCode("12002"));
			getCheckNoService().remove(no);
			return;
		} else {
			getCheckNoService().remove(no);
		}
		try {
			password = RSAUtil.decryptStrIOS(password, RSAUtil.RSAKEY_BASE_PATH);
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
			setResMsg(MsgUtil.getErrorMsgByCode("12015"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMsg("register successfully"));
	}
}
