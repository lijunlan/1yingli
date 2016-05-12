package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExOrderListUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMessageToWXUtil;

public class FGetWXPayUrlService extends MsgService {

	private OrderListService orderListService;

	private UserMarkService userMarkService;

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("olid");
	}

	@Override
	public void doit() {
		OrderList orderList = getOrderListService().queryByOrderListNo(getData().getString("olid"));
		if (orderList == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42003"));
			return;
		}
		User user = userMarkService.queryUser(getData().getString("uid"));
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		if (orderList.getUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>createOrderListId:" + orderList.getUser().getId() + ",userId:" + user.getId() + ","
					+ (orderList.getUser().getId().longValue() == user.getId().longValue()), this.getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = orderList.getState();
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String subject = ExOrderListUtil.getMultiTitle(orderList);
		// 付款金额 以分为单位
		String total_fee = String.valueOf((int) (Math.round(orderList.getPayMoney()*100F)));

		SuperMap map = new SuperMap();
		map.put("extra_param", "none");
		map.put("content", "【一英里】(" + orderList.getOrderListNo() + ")" + subject);
		map.put("oid", orderList.getOrderListNo());
		map.put("ip", getData().getString("IP").split(",")[0]);
		map.put("money", total_fee);
		String url = SendMessageToWXUtil.unifyOrder(map.finish());
		if (url.equals("")) {
			setResMsg(MsgUtil.getErrorMsgByCode("43003"));
		} else {
			setResMsg(MsgUtil.getSuccessMap().put("url", url).finishByJson());
		}
	}

}
