package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Service.DistributorService;
import cn.yiyingli.Util.MsgUtil;

public class MGetDistributorListService extends MMsgService {

	private DistributorService distributorService;

	public DistributorService getDistributorService() {
		return distributorService;
	}

	public void setDistributorService(DistributorService distributorService) {
		this.distributorService = distributorService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = Integer.valueOf((String) getData().get("page"));
		long count = getDistributorService().queryCount();
		List<Distributor> distributors = getDistributorService().queryList(page);
		ExList toSend = new ExArrayList();
		for (Distributor d : distributors) {
			SuperMap map = new SuperMap();
			map.put("id", d.getId());
			map.put("name", d.getName());
			map.put("username", d.getUsername());
			map.put("createTime", d.getCreateTime());
			map.put("lastLoginTime", d.getLastLoginTime());
			map.put("number", d.getNumber());
			map.put("sendVoucher", d.getSendVoucher());
			map.put("voucherCount", d.getVoucherCount());
			map.put("dealNumber", d.getDealNumber());
			map.put("orderNumber", d.getOrderNumber());
			map.put("registerNumber", d.getRegisterNumber());
			map.put("voucherMoney", d.getVoucherMoney());
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("count", count).put("data", toSend).finishByJson());
	}

}
