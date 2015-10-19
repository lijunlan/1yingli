package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Service.CheckNoService;
import cn.yiyingli.Util.CheckNoFactory;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMailUtil;
import cn.yiyingli.Util.SendMessageUtil;

public class GetCheckNoService extends MsgService {

	private CheckNoService checkNoService;

	public CheckNoService getCheckNoService() {
		return checkNoService;
	}

	public void setCheckNoService(CheckNoService checkNoService) {
		this.checkNoService = checkNoService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("username");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		if (!(CheckUtil.checkMobileNumber(username) || CheckUtil.checkEmail(username))) {
			setResMsg(MsgUtil.getErrorMsg("username is not accurate"));
			return;
		}
		CheckNo cn = getCheckNoService().query(username);
		if (cn != null) {
			long time = Calendar.getInstance().getTimeInMillis();
			if (time > Long.valueOf(cn.getEndTime())) {
				getCheckNoService().remove(cn);
			} else {
				setResMsg(MsgUtil.getErrorMsg("you can not get now"));
				return;
			}
		}
		String checkNo = CheckNoFactory.createCheckNo();
		CheckNo no = new CheckNo();
		no.setUsername(username);
		no.setCheckNo(checkNo);
		long startTime = Calendar.getInstance().getTimeInMillis();
		long endTime = startTime + 3 * 60 * 1000;
		no.setCreateTime(String.valueOf(startTime));
		no.setEndTime(String.valueOf(endTime));
		getCheckNoService().save(no);
		boolean result = false;
		{
			if (CheckUtil.checkMobileNumber(username))
				result = SendMessageUtil.sendCheckNo(username, checkNo);
			else
				result = SendMailUtil.sendMail(username, checkNo);
		}
		if (result) {
			setResMsg(MsgUtil.getSuccessMsg("get checkNO successfully"));
		} else {
			setResMsg(MsgUtil.getErrorMsg("get checkNO failed"));
		}
	}

}
