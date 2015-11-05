package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetOrderListByNameService extends MMsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("name") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		super.doit();
		SuperMap toSend = MsgUtil.getSuccessMap();
		String name = (String) getData().get("name");
		int page = 0;
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
