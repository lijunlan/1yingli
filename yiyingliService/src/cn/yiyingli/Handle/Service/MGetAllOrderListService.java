package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class MGetAllOrderListService extends MMsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = Integer.parseInt((String) getData().get("page"));
		List<Order> orders = getOrderService().queryListAll(page);
		SuperMap toSend = MsgUtil.getSuccessMap();
		ExList sends = new ExArrayList();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToManager(map, o);
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());

	}

}
