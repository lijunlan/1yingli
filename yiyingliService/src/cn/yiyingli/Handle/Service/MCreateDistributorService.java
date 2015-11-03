package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.DistributorService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class MCreateDistributorService extends MsgService {

	private ManagerMarkService managerMarkService;

	private DistributorService distributorService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	public DistributorService getDistributorService() {
		return distributorService;
	}

	public void setDistributorService(DistributorService distributorService) {
		this.distributorService = distributorService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("username") && getData().containsKey("password")
				&& getData().containsKey("voucherMoney") && getData().containsKey("name")
				&& getData().containsKey("sendVoucher") && getData().containsKey("voucherCount");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
		}
		String username = (String) getData().get("username");
		String password = (String) getData().get("password");
		String name = (String) getData().get("name");
		float voucherMoney = Float.valueOf((String) getData().get("voucherMoney"));
		boolean sendVoucher = Boolean.valueOf((String) getData().get("sendVoucher"));
		int voucherCount = Integer.valueOf((String) getData().get("voucherCount"));
		try {
			password = RSAUtil.decryptStr(password, RSAUtil.RSAKEY_BASE_PATH);
		} catch (Exception e1) {
			e1.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("server error"));
			return;
		}
		if (!CheckUtil.checkPassword(password)) {
			setResMsg(MsgUtil.getErrorMsg("BAD PASSWROD!"));
			return;
		}
		password = MD5Util.MD5(password);

		Distributor distributor = new Distributor();
		distributor.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		distributor.setDealNumber(0L);
		distributor.setOrderNumber(0L);
		distributor.setRegisterNumber(0L);
		distributor.setSendVoucher(sendVoucher);
		distributor.setVoucherCount(voucherCount);
		distributor.setUsername(username);
		distributor.setVoucherMoney(voucherMoney);
		distributor.setName(name);
		distributor.setNumber(MD5Util.MD5(UUID.randomUUID().toString()));
		distributor.setPassword(password);

		getDistributorService().save(distributor);

		setResMsg(MsgUtil.getErrorMsg("create distributor successfully"));
	}

}
