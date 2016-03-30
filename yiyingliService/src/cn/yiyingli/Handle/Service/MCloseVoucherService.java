package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.MsgUtil;

public class MCloseVoucherService extends MMsgService {

	private VoucherService voucherService;

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("voucherId");
	}

	@Override
	public void doit() {
		long voucherId = getData().getLong("voucherId");
		Voucher voucher = getVoucherService().query(voucherId, false);
		voucher.setUsed(true);
		getVoucherService().update(voucher);
		setResMsg(MsgUtil.getSuccessMsg("close voucher successfully"));
	}

}
