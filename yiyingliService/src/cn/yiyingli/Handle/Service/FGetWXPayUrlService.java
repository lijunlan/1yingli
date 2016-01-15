package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMessageToWXUtil;

public class FGetWXPayUrlService extends MsgService {

	private OrderService orderService;

	private UserMarkService userMarkService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("oid");
	}

	@Override
	public void doit() {
		Order order = getOrderService().queryByShowId(getData().getString("oid"), false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		User user = userMarkService.queryUser(getData().getString("uid"));
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>createOrderId:" + order.getCreateUser().getId() + ",userId:" + user.getId() + ","
					+ (order.getCreateUser().getId() == user.getId()), this.getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = order.getState();
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String subject = order.getServiceTitle();
		// 付款金额 以分为单位
		String total_fee = String.valueOf((int) (order.getMoney() * 100F));

		SuperMap map = new SuperMap();
		map.put("extra_param", "none");
		map.put("content", "【一英里】(" + order.getTeacher().getName() + ")" + subject);
		map.put("oid", order.getOrderNo());
		map.put("ip", getData().getString("IP"));
		map.put("money", total_fee);
		String url = SendMessageToWXUtil.unifyOrder(map.finish());
		if (url.equals("")) {
			setResMsg(MsgUtil.getErrorMsgByCode("43003"));
		} else {
			setResMsg(MsgUtil.getSuccessMap().put("url", url).finishByJson());
		}
	}

}
