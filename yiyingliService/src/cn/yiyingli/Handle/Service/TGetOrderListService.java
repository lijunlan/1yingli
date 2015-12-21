package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class TGetOrderListService extends TMsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page") || getData().containsKey("state");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = teacher.getOrderNumber();
		toSend.put("count", count);
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		List<Order> orders = null;
		if (getData().containsKey("state")) {
			String s = (String) getData().get("state");
			String states[] = s.split("\\|");
			orders = getOrderService().queryListByTeacherId(teacher.getId(), states, page, false);
		} else {
			orders = getOrderService().queryListByTeacherId(teacher.getId(), page, false);
		}
		ExList sends = new ExArrayList();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToTeacher(map, o);
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());
	}

}
