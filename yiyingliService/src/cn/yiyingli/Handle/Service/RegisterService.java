package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CheckNoService;
import cn.yiyingli.Service.DistributorService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;
import cn.yiyingli.toPersistant.PUserUtil;

public class RegisterService extends MsgService {

	private UserService userService;

	private CheckNoService checkNoService;

	private DistributorService distributorService;

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

	public DistributorService getDistributorService() {
		return distributorService;
	}

	public void setDistributorService(DistributorService distributorService) {
		this.distributorService = distributorService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("username") && getData().containsKey("password")
				&& getData().containsKey("checkNo") && getData().containsKey("nickName")
				|| getData().containsKey("distributorNO");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		String checkNo = (String) getData().get("checkNo");
		String nickName = (String) getData().get("nickName");
		Distributor distributor = null;
		if (nickName.length() > 7) {
			nickName = nickName.substring(0, 7);
		}
		if (!(CheckUtil.checkMobileNumber(username) || CheckUtil.checkEmail(username)) || "".equals(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("12017"));
			return;
		}

		CheckNo no = getCheckNoService().query(username);
		long time = Calendar.getInstance().getTimeInMillis();
		if (no == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("52002"));
			return;
		}
		if (!(no.getCheckNo().equals(checkNo))) {
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
		if (getData().containsKey("distributorNO")) {
			String dno = (String) getData().get("distributorNO");
			distributor = getDistributorService().queryByNo(dno);
			if (distributor == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("62001"));
				return;
			}
		}
		password = MD5Util.MD5(password);
		User user = PUserUtil.assembleUser(username, password, nickName, null, null, distributor);
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
