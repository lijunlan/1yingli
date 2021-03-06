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

public class IOSForgetPasswordService extends MsgService {

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
		return getData().containsKey("no") && getData().containsKey("password") && getData().containsKey("checkNo");
	}

	@Override
	public void doit() {
		String no = (String) getData().get("no");
		String password = (String) getData().get("password");
		String checkNo = (String) getData().get("checkNo");
		if (!(CheckUtil.checkEmail(no) || CheckUtil.checkMobileNumber(no))) {
			setResMsg(MsgUtil.getErrorMsgByCode("12013"));
			return;
		}
		CheckNo mNo = getCheckNoService().query(no);
		long time = Calendar.getInstance().getTimeInMillis();

		if (mNo == null || !(mNo.getCheckNo().equals(checkNo))) {
			setResMsg(MsgUtil.getErrorMsgByCode("12001"));
			return;
		} else if (time > Long.valueOf(mNo.getEndTime())) {
			setResMsg(MsgUtil.getErrorMsgByCode("12002"));
			getCheckNoService().remove(mNo);
			return;
		} else {
			getCheckNoService().remove(mNo);
		}
		User user = getUserService().query(no, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("15003"));
			return;
		}
		try {
			password = RSAUtil.decryptStrIOS(password, RSAUtil.RSAKEY_BASE_PATH);
			if (!CheckUtil.checkPassword(password)) {
				setResMsg(MsgUtil.getErrorMsgByCode("12005"));
				return;
			}
			password = MD5Util.MD5(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("10001"));
			return;
		}
		user.setPassword(password);
		getUserService().update(user);
		setResMsg(MsgUtil.getSuccessMsg("password has changed"));
	}

}
