package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.CouponNumberUtil;
import cn.yiyingli.Util.MsgUtil;

public class MCreateVoucherService extends MsgService {

	private VoucherService voucherService;

	private ManagerMarkService managerMarkService;

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("count") && getData().containsKey("endTime")
				&& getData().containsKey("startTime") && getData().containsKey("money");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		int count = Integer.valueOf((String) getData().get("count"));
		float money = Float.valueOf((String) getData().get("money"));
		String endTime = (String) getData().get("endTime");
		String startTime = (String) getData().get("startTime");
		int sum = 0;
		for (int i = 1; i <= count; i++) {
			Voucher voucher = new Voucher();
			voucher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			voucher.setEndTime(endTime);
			voucher.setMoney(money);
			voucher.setNumber(CouponNumberUtil.getNewNumber());
			voucher.setStartTime(startTime);
			voucher.setUsed(false);
			try {
				getVoucherService().save(voucher);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			sum++;
		}
		setResMsg(MsgUtil.getSuccessMap().put("sum", sum).finishByJson());

	}

}
