package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderListUtil;
import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class TGetOrderListService extends TMsgService {

	private OrderListService orderListService;

	private OrderService orderService;

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");// || getData().containsKey("state");
	}


	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
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

		int type = 0;
		if (getData().containsKey("type")) {
			type = (int) getData().get("type");
		}
		List<Order> orders;


		String s = OrderService.TEACHER_ORDER_TYPE_STATES[type];
		String states[] = s.split("\\|");
		orders = getOrderService().queryListByTeacherId(teacher.getId(), states, page, false);
		long count = getOrderService().querySumNoByTeacherIdAndStates(teacher.getId(),states);
		toSend.put("count", count);

		ExList toSendOrders = new ExArrayList();
		for (Order order : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToTeacher(map, order);
			toSendOrders.add(map.finish());
		}
		setResMsg(toSend.put("data", toSendOrders).finishByJson());
//		long count = teacher.getOrderNumber();
//		toSend.put("count", count);
//
//		List<OrderList> orderLists = getOrderListService().queryListByTeacher(teacher.getId(), page);
//
//		ExOrderListUtil.removeUnuseOrder(orderLists);

//		if (getData().containsKey("state")) {
//			String s = (String) getData().get("state");
//			String states[] = s.split("\\|");
//			ExOrderListUtil.getMatchStateLists(orderLists, states);
//		}

//		if (getData().containsKey("type")) {
//			String s = orderType[(int) getData().get("type")];
//			String states[] = s.split("\\|");
//			ExOrderListUtil.getMatchStateLists(orderLists, states);
//		}

//		ExList toSendOrderLists = new ExArrayList();
//		for (OrderList orderList : orderLists) {
//			SuperMap map = new SuperMap();
//			ExOrderListUtil.assembleOrderListToTeacher(orderList, map);
//			toSendOrderLists.add(map.finish());
//		}

//		setResMsg(toSend.put("data", toSendOrderLists).finishByJson());
	}

}
