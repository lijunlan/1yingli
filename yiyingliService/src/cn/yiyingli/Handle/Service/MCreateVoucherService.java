package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.CouponNumberUtil;
import cn.yiyingli.Util.MsgUtil;

public class MCreateVoucherService extends MMsgService {

	private VoucherService voucherService;

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("count") && getData().containsKey("endTime")
				&& getData().containsKey("startTime") && getData().containsKey("money");
	}

	@Override
	public void doit() {
		Manager manager = getManager();
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
			voucher.setNumber(CouponNumberUtil.getNewNumber(10));
			voucher.setStartTime(startTime);
			voucher.setOrigin("Manager:" + manager.getId() + "," + manager.getName());
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
