package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Service.DistributorService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.RSAUtil;

public class MCreateDistributorService extends MMsgService {

	private DistributorService distributorService;

	public DistributorService getDistributorService() {
		return distributorService;
	}

	public void setDistributorService(DistributorService distributorService) {
		this.distributorService = distributorService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("username") && getData().containsKey("password")
				&& getData().containsKey("voucherMoney") && getData().containsKey("name")
				&& getData().containsKey("sendVoucher") && getData().containsKey("voucherCount");
	}

	@Override
	public void doit() {
		super.doit();
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
			setResMsg(MsgUtil.getErrorMsgByCode("30001"));
			return;
		}
		if (!CheckUtil.checkPassword(password)) {
			setResMsg(MsgUtil.getErrorMsgByCode("32001"));
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
