package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.Json;
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
		List<String> toSend = new ArrayList<String>();
		List<Voucher> listV = getVoucherService().queryList(page, false);
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
			map.put("orderId", v.getUseOrder() == null ? "" : v.getUseOrder().getOrderNo());
			if (v.getUseOrder() == null) {
				map.put("userId", "");
			} else {
				map.put("userId", v.getUseOrder().getOrderNo());
			}
			toSend.add(map.finishByJson());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", Json.getJson(toSend)).finishByJson());
	}

}
