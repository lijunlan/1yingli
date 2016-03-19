package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderListUtil;
import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderListService extends UMsgService {

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


	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yiyingli.Handle.MsgService#doit()
	 */
	@Override
	public void doit() {
		User user = getUser();
		int page = 0;
		int type = 0;
		if (getData().containsKey("type")) {
			type = (int) getData().get("type");
		}
		String s = OrderService.USER_ORDER_TYPE_STATES[type];
		String[] states = s.split("\\|");

		Long count = getOrderService().querySumNoByUserIdAndStates(user.getId(), states);
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", count);
		if (getData().get("page").equals("max")) {
			if (count % OrderService.PAGE_SIZE_INT > 0)
				page = (int) (count / OrderService.PAGE_SIZE_INT) + 1;
			else
				page = (int) (count / OrderService.PAGE_SIZE_INT);
			if (page == 0)
				page = 1;
			toSend.put("page", page);
		} else {
			try {
				page = Integer.parseInt((String) getData().get("page"));
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsgByCode("12010"));
				return;
			}
			if (page <= 0) {
				setResMsg(MsgUtil.getErrorMsgByCode("12010"));
				return;
			}
		}


		List<Order> orders = getOrderService().queryListByUserId(user.getId(), states, page, false);

		ExList toSendOrders = new ExArrayList();
		for (Order order : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToUser(map, order);
			toSendOrders.add(map.finish());
		}
		setResMsg(toSend.put("data", toSendOrders).finishByJson());

//		List<OrderList> orderLists = getOrderListService().queryListByUser(user.getId(), page);
//		if (getData().containsKey("state")) {
//			String s = (String) getData().get("state");
//			String states[] = s.split("\\|");
//			ExOrderListUtil.getMatchStateLists(orderLists, states);
//		}
//
//		if (getData().containsKey("type")) {
//			String s = OrderService.USER_ORDER_TYPE_STATES[(int) getData().get("type")];
//			String states[] = s.split("\\|");
//			ExOrderListUtil.getMatchStateLists(orderLists, states);
//		}
//
//		ExList toSendOrderLists = new ExArrayList();
//		for (OrderList orderList : orderLists) {
//			SuperMap map = new SuperMap();
//			ExOrderListUtil.assembleOrderListToUser(orderList, map);
//			toSendOrderLists.add(map.finish());
//		}
//
//		setResMsg(toSend.put("data", toSendOrderLists).finishByJson());

	}

}
