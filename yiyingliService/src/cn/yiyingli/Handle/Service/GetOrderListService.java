package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderListService extends UMsgService {

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
		User user = getUser();
		int page = 0;
		long count = user.getOrderNumber();
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
		List<Order> orders = null;
		if (getData().containsKey("state")) {
			String s = (String) getData().get("state");
			String states[] = s.split("\\|");
			orders = getOrderService().queryListByUserId(user.getId(), states, page, false);
		} else {
			orders = getOrderService().queryListByUserId(user.getId(), page, false);
		}
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			ExOrderUtil.assembleOrderToUser(map, o);
			sends.add(map.finishByJson());
		}
		toSend.put("state", "success");
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());

	}

}
