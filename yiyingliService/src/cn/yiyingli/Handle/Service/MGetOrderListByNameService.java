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

public class MGetOrderListByNameService extends MsgService {

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
		return getData().containsKey("mid") && getData().containsKey("name") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		String name = (String) getData().get("name");
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
		List<Order> orders = getOrderService().queryListByName(name, page);
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToManager(map, o);
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
	}

}
