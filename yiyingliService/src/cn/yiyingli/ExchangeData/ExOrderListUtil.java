package cn.yiyingli.ExchangeData;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;

public class ExOrderListUtil {

	public static void assembleOrderListToUser(OrderList orderList, SuperMap map) {
		map.put("batchNo", orderList.getOrderListNo());
		map.put("teacherId", orderList.getTeacher().getId());
		map.put("teacherName", orderList.getTeacher().getName());
		map.put("teacherUrl", orderList.getTeacher().getIconUrl());
		map.put("payMoney", orderList.getPayMoney());
		map.put("nowMoney", orderList.getNowMoney());
		map.put("originMoney", orderList.getOriginMoney());
		ExList toSendOrders = new ExArrayList();
		for (Order o : orderList.getOrders()) {
			SuperMap jsonorder = new SuperMap();
			ExOrderUtil.assembleOrderToUser(jsonorder, o);
			toSendOrders.add(jsonorder.finish());
		}
	}

	public static String getMultiTitle(OrderList orderList) {
		List<Order> orders = orderList.getOrders();
		String subject = orders.get(0).getServiceTitle() + (orders.size() > 1 ? "等" + orders.size() + "项" : "");
		return subject;
	}

	public static void getMatchStateLists(List<OrderList> orderLists, String[] states) {
		for (OrderList orderList : orderLists) {
			List<Order> toDelete = new ArrayList<Order>();
			for (Order order : orderList.getOrders()) {
				String ostate = order.getState().split(",")[0];
				boolean flag = false;
				for (String state : states) {
					if (state.equals(ostate)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					toDelete.add(order);
				}
			}
			for (Order order : toDelete) {
				orderList.getOrders().remove(order);
			}
		}
	}
}
