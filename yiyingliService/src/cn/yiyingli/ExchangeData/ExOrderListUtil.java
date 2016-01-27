package cn.yiyingli.ExchangeData;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;

public class ExOrderListUtil {

	public static void assembleOrderListToTeacher(OrderList orderList, SuperMap map) {
		map.put("batchNo", orderList.getOrderListNo());
		map.put("userId", orderList.getUser().getId());
		map.put("userName", orderList.getUser().getName());
		map.put("userUrl", orderList.getUser().getIconUrl());
		map.put("payMoney", orderList.getPayMoney());
		map.put("nowMoney", orderList.getNowMoney());
		map.put("originMoney", orderList.getOriginMoney());
		map.put("createTime", orderList.getCreateTime());
		ExList toSendOrders = new ExArrayList();
		for (Order o : orderList.getOrders()) {
			SuperMap jsonorder = new SuperMap();
			ExOrderUtil.assembleOrderToTeacher(jsonorder, o);
			toSendOrders.add(jsonorder.finish());
		}
		map.put("orders", toSendOrders);
	}

	public static void assembleOrderListToUser(OrderList orderList, SuperMap map) {
		map.put("batchNo", orderList.getOrderListNo());
		map.put("teacherId", orderList.getTeacher().getId());
		map.put("teacherName", orderList.getTeacher().getName());
		map.put("teacherUrl", orderList.getTeacher().getIconUrl());
		map.put("payMoney", orderList.getPayMoney());
		map.put("nowMoney", orderList.getNowMoney());
		map.put("originMoney", orderList.getOriginMoney());
		map.put("createTime", orderList.getCreateTime());
		map.put("state", orderList.getState());
		ExList toSendOrders = new ExArrayList();
		for (Order o : orderList.getOrders()) {
			SuperMap jsonorder = new SuperMap();
			ExOrderUtil.assembleOrderToUser(jsonorder, o);
			toSendOrders.add(jsonorder.finish());
		}
		map.put("orders", toSendOrders);
	}

	public static String getMultiTitle(OrderList orderList) {
		List<Order> orders = orderList.getOrders();
		String subject = orders.get(0).getServiceTitle() + (orders.size() > 1 ? "等" + orders.size() + "项" : "");
		return subject;
	}

	public static void removeUnuseOrder(List<OrderList> orderLists) {
		List<OrderList> toRemoveOrderList = new ArrayList<OrderList>();
		for (OrderList orderlist : orderLists) {
			List<Order> toRemove = new ArrayList<Order>();
			for (Order order : orderlist.getOrders()) {
				String state = order.getState();
				if (state.contains("0200,0100") || state.startsWith("0100")) {
					toRemove.add(order);
				}
			}
			for (Order order : toRemove) {
				orderlist.getOrders().remove(order);
			}
			if (orderlist.getOrders().size() == 0) {
				toRemoveOrderList.add(orderlist);
			}
		}
		for (OrderList orderlist : toRemoveOrderList) {
			orderLists.remove(orderlist);
		}
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
