package cn.yiyingli.Handle.Service;


import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.*;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.MsgUtil;
import net.sf.json.JSONArray;

import java.util.Calendar;
import java.util.List;

public class CreatePayService extends UMsgService {


	private OrderService orderService;

	private OrderListService orderListService;

	private VoucherService voucherService;

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

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderList");
	}

	@Override
	public void doit() {
		User user = getUser();
		JSONArray jsonServiceList = getData().getJSONArray("orderList");
		long[] ids = new long[jsonServiceList.size()];
		for (int i = 0; i < jsonServiceList.size(); i++) {
			ids[i] = jsonServiceList.getLong(i);
		}
		List<Order> orders = getOrderService().queryListByOrderNos(ids);
		long ntime = Calendar.getInstance().getTimeInMillis();
		OrderList orderList = new OrderList();
		orderList.setCreateTime(ntime + "");
		orderList.setState(OrderListService.ORDER_STATE_NOT_PAID);
		orderList.setOriginMoney(0F);
		orderList.setNowMoney(0F);
		orderList.setPayMoney(0F);
		orderList.setUser(user);
		for (Order order : orders) {
			if (!((order.getState().equals(OrderService.ORDER_STATE_NOT_PAID)
					&& (order.getServiceType() == null || order.getServiceType().equals(ServicePro.SERVICE_TYPE_NORMAL)))
					|| (order.getState().equals(OrderService.ORDER_BARGAINED_NOT_PAID)
					&& order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)))) {
				setResMsg(MsgUtil.getErrorMsgByCode("44002"));
				return;
			}
			orderList.setOriginMoney(orderList.getOriginMoney() + order.getOriginMoney());
			orderList.setNowMoney(orderList.getNowMoney() + order.getMoney());
			orderList.getOrders().add(order);
		}
		Voucher voucher = null;
		float money = orderList.getNowMoney();
		if (getData().containsKey("voucher")) {
			String vno = (String) getData().get("voucher");
			voucher = getVoucherService().query(vno, false);
			if (voucher == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("45001"));
				return;
			} else if (ntime > Long.valueOf(voucher.getEndTime()) || ntime < Long.valueOf(voucher.getStartTime())) {
				setResMsg(MsgUtil.getErrorMsgByCode("45002"));
				return;
			} else if (voucher.getUsed()) {
				setResMsg(MsgUtil.getErrorMsgByCode("45003"));
				return;
			} else if (voucher.getServiceProId() != null) {
				if (orderList.getOrders().size() == 1 && orderList.getOrders().get(0).getServiceId() != null
						&& orderList.getOrders().get(0).getServiceId().longValue() == voucher.getServiceProId()
						.longValue()) {
					money = money - voucher.getMoney();
					orderList.setVoucher(voucher);
				} else {
					setResMsg(MsgUtil.getErrorMsgByCode("45006"));
					return;
				}
			} else {
				money = money - voucher.getMoney();
				voucher.setUsed(true);
				orderList.setVoucher(voucher);
			}
		}
		if (money < 0.01) {
			money = 0.01F;
		}
		orderList.setPayMoney(money);
		String r = getOrderListService().saveAndSubCount(orderList);
		if (r.equals(OrderListService.ORDER_ERROR_COUNT_LIMITED)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44009"));
			return;
		}
		SuperMap toSend =MsgUtil.getSuccessMap();
		toSend.put("orderNoList", orderList.getOrderListNo())
				.put("msg", "create pay successfully")
				.put("originMoney",orderList.getNowMoney());
		if (orderList.getVoucher() != null) {
			toSend.put("payMoney",orderList.getPayMoney());
		}
		setResMsg(toSend.finishByJson());
	}
}
