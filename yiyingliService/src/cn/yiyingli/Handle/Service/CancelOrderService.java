package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.AlipayCancelUtil;
import cn.yiyingli.Util.LogUtil;

/**
 * 取消订单，仅存在于还没生成支付宝订单的情况下
 * 
 * @author sdll18
 *
 */
public class CancelOrderService extends UMsgService {

	private OrderListService orderListService;

	private NotificationService notificationService;

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("olid");
	}

	@Override
	public void doit() {
		User user = getUser();
		String olid = (String) getData().get("olid");
		OrderList orderList = getOrderListService().queryByOrderListNo(olid);
		if (orderList == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42003"));
			return;
		}
		if (orderList.getUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>cancelOrderListId:" + orderList.getUser().getId() + ",userId:" + user.getId() + ","
					+ (orderList.getUser().getId().longValue() == user.getId().longValue()), this.getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = orderList.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}

		try {
			AlipayCancelUtil.cancelOrder("", olid);
		} catch (Exception e) {
			e.printStackTrace();
			// setResMsg(MsgUtil.getErrorMsgByCode("43001"));
		}
		cancelOrderList(orderList);

		NotifyUtil.notifyUserOrder(orderList, "尊敬的学员，您的订单已经取消。订单号" + orderList.getOrderListNo(), user,
				getNotificationService());
		setResMsg(MsgUtil.getSuccessMsg("cancel orderList successfully"));

	}

	private void cancelOrderList(OrderList orderList) {
		orderList.setState(OrderListService.ORDER_STATE_CANCEL_PAID + "," + orderList.getState());
		for (Order order : orderList.getOrders()) {
			order.setState(OrderService.ORDER_STATE_END_FAILED + "," + order.getState());
		}
		getOrderListService().updateAndAddCount(orderList);
	}

}
