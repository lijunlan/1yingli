package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExOrderListUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderListService extends UMsgService {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yiyingli.Handle.MsgService#doit()
	 */
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
		List<OrderList> orderLists = getOrderListService().queryListByUser(user.getId(), page);
		if (getData().containsKey("state")) {
			String s = (String) getData().get("state");
			String states[] = s.split("\\|");
			ExOrderListUtil.getMatchStateLists(orderLists, states);
		}

		ExList toSendOrderLists = new ExArrayList();
		for (OrderList orderList : orderLists) {
			SuperMap map = new SuperMap();
			ExOrderListUtil.assembleOrderListToUser(orderList, map);
			toSendOrderLists.add(map.finish());
		}

		setResMsg(toSend.put("data", toSendOrderLists).finishByJson());

	}

}
