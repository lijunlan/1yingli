package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Service.DistributorMarkService;
import cn.yiyingli.Service.DistributorService;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class DLoginService extends MsgService {

	private DistributorService distributorService;

	private DistributorMarkService distributorMarkService;

	public DistributorService getDistributorService() {
		return distributorService;
	}

	public void setDistributorService(DistributorService distributorService) {
		this.distributorService = distributorService;
	}

	public DistributorMarkService getDistributorMarkService() {
		return distributorMarkService;
	}

	public void setDistributorMarkService(DistributorMarkService distributorMarkService) {
		this.distributorMarkService = distributorMarkService;
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
			setResMsg(MsgUtil.getErrorMsgByCode("62002"));
			return;
		}
		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
			password = MD5Util.MD5(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("60001"));
			return;
		}
		Distributor distributor = getDistributorService().queryByUsername(username);
		if (distributor == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("62003"));
			return;
		}
		if (password.equals(distributor.getPassword())) {
			String _UUID = UUID.randomUUID().toString();
			getDistributorMarkService().save(String.valueOf(distributor.getId()), _UUID);
			distributor.setLastLoginTime(Calendar.getInstance().getTimeInMillis() + "");
			getDistributorService().update(distributor);
			setResMsg("{\"did\":\"" + _UUID + "\",\"state\":\"success\",\"dname\":\"" + distributor.getName() + "\"}");
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("62004"));
		}
	}
}
