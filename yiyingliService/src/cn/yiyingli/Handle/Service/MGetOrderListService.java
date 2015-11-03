package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetOrderListService extends MsgService {

	private ManagerMarkService managerMarkService;

	private OrderService orderService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("page")
				&& (getData().containsKey("state") || getData().containsKey("salaryState"))
				|| getData().containsKey("rank");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		List<Order> orders;
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		if (getData().containsKey("salaryState")) {
			short salaryState = Short.parseShort((String) getData().get("salaryState"));
			int page = 0;
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
			orders = getOrderService().queryListBySalaryState(salaryState, page,
					getData().get("rank") == null ? null : (String) getData().get("rank"));
		} else {
			String state = (String) getData().get("state");
			int page = 0;
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
			orders = getOrderService().queryListByState(state, page, false,
					getData().get("rank") == null ? null : (String) getData().get("rank"));
		}
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToManager(map, o);
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());

	}

}
