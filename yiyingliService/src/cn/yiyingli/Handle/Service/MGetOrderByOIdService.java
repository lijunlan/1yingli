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

public class MGetOrderByOIdService extends MMsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("id");
	}

	@Override
	public void doit() {
		String id = (String) getData().get("id");
		SuperMap toSend = MsgUtil.getSuccessMap();
		Order o = getOrderService().queryByOrderNo(id);
		if (o == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		List<String> sends = new ArrayList<String>();
		SuperMap map = new SuperMap();
		ExOrderUtil.assembleOrderToManager(map, o);
		sends.add(map.finishByJson());
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
	}

}
