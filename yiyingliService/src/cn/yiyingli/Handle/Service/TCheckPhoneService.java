package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Service.CheckNoService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MsgUtil;

public class TCheckPhoneService extends TMsgService {

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
		return super.checkData() && getData().containsKey("checkNo") && getData().containsKey("phone");
	}

	@Override
	public void doit() {
		String phone = (String) getData().get("phone");
		String checkNo = (String) getData().get("checkNo");
		CheckNo no = getCheckNoService().query(phone);
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
		if (CheckUtil.checkMobileNumber(phone)) {
			if (getTeacher().getPhone().equals(phone)) {
				getTeacher().setCheckPhone(true);
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("22003"));
				return;
			}
			getTeacherService().update(getTeacher());
			setResMsg(MsgUtil.getSuccessMsg("phone number has been checked"));
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("22004"));
		}
	}

}
