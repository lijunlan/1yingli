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

public class MGetOrderListService extends MMsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page")
				&& (getData().containsKey("state") || getData().containsKey("salaryState"))
				|| getData().containsKey("rank");
	}

	@Override
	public void doit() {
		List<Order> orders;
		SuperMap toSend = MsgUtil.getSuccessMap();
		if (getData().containsKey("salaryState")) {
			short salaryState = Short.parseShort((String) getData().get("salaryState"));
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
			orders = getOrderService().queryListBySalaryState(salaryState, page,
					getData().get("rank") == null ? null : (String) getData().get("rank"));
		} else {
			String state = (String) getData().get("state");
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
			orders = getOrderService().queryListByState(state, page, false,
					getData().get("rank") == null ? null : (String) getData().get("rank"));
		}
		ExList sends = new ExArrayList();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToManager(map, o);
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());
	}

}
