package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.AdditionalPay;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

import java.util.Calendar;

public class CreateAdditionalPayService extends UMsgService {


	private OrderService orderService;

	private OrderListService orderListService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("money")
				&& getData().containsKey("reason");
	}

	@Override
	public void doit() {
		User user = getUser();
		String reason = (String) getData().get("reason");
		float money = Float.parseFloat((String) getData().get("money"));
		if (money < 0.01) {
			money = 0.01F;
		}
		String orderId = (String) getData().get("orderId");
		Order order = getOrderService().queryByOrderNo(orderId);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		long ntime = Calendar.getInstance().getTimeInMillis();

		AdditionalPay additionalPay = new AdditionalPay();
		additionalPay.setCreateTime(ntime + "");
		additionalPay.setMoney(money);
		additionalPay.setReason(reason);
		additionalPay.setOrder(order);
		additionalPay.setState(AdditionalPay.ADDITIONALPAY_STATE_NOT_PAID);
		additionalPay.setUser(user);

		OrderList orderList = new OrderList();
		orderList.setCreateTime(ntime + "");
		orderList.setState(OrderListService.ORDER_STATE_NOT_PAID);
		orderList.setOriginMoney(money);
		orderList.setNowMoney(money);
		orderList.setPayMoney(money);
		orderList.setUser(user);
		orderList.getAdditionalPays().add(additionalPay);

		String r = getOrderListService().saveAndSubCount(orderList);
		if (r.equals(OrderListService.ORDER_ERROR_COUNT_LIMITED)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44009"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMap()
				.put("orderNoList", orderList.getOrderListNo())
				.put("originMoney",orderList.getNowMoney())
				.put("msg", "create additionalPay successfully").finishByJson());
	}
}
