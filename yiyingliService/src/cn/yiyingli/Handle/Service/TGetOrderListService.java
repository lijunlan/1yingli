package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderListUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Util.MsgUtil;

public class TGetOrderListService extends TMsgService {

	private OrderListService orderListService;

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
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

		List<OrderList> orderLists = getOrderListService().queryListByTeacher(teacher.getId(), page);

		ExOrderListUtil.removeUnuseOrder(orderLists);

		if (getData().containsKey("state")) {
			String s = (String) getData().get("state");
			String states[] = s.split("\\|");
			ExOrderListUtil.getMatchStateLists(orderLists, states);
		}

		ExList toSendOrderLists = new ExArrayList();
		for (OrderList orderList : orderLists) {
			SuperMap map = new SuperMap();
			ExOrderListUtil.assembleOrderListToTeacher(orderList, map);
			toSendOrderLists.add(map.finish());
		}

		setResMsg(toSend.put("data", toSendOrderLists).finishByJson());
	}

}
