package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetVoucherListService extends MsgService {

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
		return getData().containsKey("mid") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		int page;
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsg("page is wrong"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsg("page is wrong"));
			return;
		}
		List<String> toSend=new ArrayList<String>();
		List<Voucher> listV=getVoucherService().queryList(page, false);
		for(Voucher v:listV){
			SuperMap map = new SuperMap();
			map.put("createTime", v.getCreateTime());
			map.put("endTime", v.getEndTime());
			map.put("number", v.getNumber());
			map.put("startTime", v.getStartTime());
			map.put("used", v.getUsed());
			map.put("id", v.getId());
			map.put("money", v.getMoney());
			if(v.getUseOrder()==null){
				map.put("userId","");
			}else{
			map.put("userId", v.getUseOrder().getOrderNo());}
			toSend.add(map.finishByJson());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data",Json.getJson(toSend)).finishByJson());
	}

}
