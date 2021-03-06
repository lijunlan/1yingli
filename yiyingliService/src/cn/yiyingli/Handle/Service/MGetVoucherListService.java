package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.MsgUtil;

public class MGetVoucherListService extends MMsgService {

	private VoucherService voucherService;

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page;
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		ExList toSend = new ExArrayList();
		List<Voucher> listV = getVoucherService().queryList(page);
		for (Voucher v : listV) {
			SuperMap map = new SuperMap();
			map.put("createTime", v.getCreateTime());
			map.put("endTime", v.getEndTime());
			map.put("number", v.getNumber());
			map.put("startTime", v.getStartTime());
			map.put("used", v.getUsed());
			map.put("id", v.getId());
			map.put("money", v.getMoney());
			map.put("origin", v.getOrigin());
			map.put("serviceProId", v.getServiceProId());
			map.put("orderListCount", v.getUseCount());
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}

}
