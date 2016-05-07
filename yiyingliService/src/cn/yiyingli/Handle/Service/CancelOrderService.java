package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
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

	private OrderService orderService;

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

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		User user = getUser();
		String orderId = (String) getData().get("orderId");
		Order order = getOrderService().queryByOrderNo(orderId);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>cancelOrderListId:" + order.getCreateUser().getId() + ",userId:" + user.getId() + ","
					+ (order.getCreateUser().getId().longValue() == user.getId().longValue()), this.getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!((OrderService.ORDER_STATE_NOT_PAID.equals(state) &&
				(order.getServiceType() == null || order.getServiceType().equals(ServicePro.SERVICE_TYPE_NORMAL)))
				|| (OrderService.ORDER_BARGAINED_NOT_PAID.equals(state) &&
				order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		order.setState(OrderService.ORDER_STATE_END_FAILED + "," + OrderService.ORDER_STATE_CANCEL_PAID + ","
				+ order.getState());
		getOrderService().updateAndAddCount(order);
//		NotifyUtil.notifyUserOrder(orderList, "尊敬的学员，您的订单已经取消。订单号" + orderList.getOrderListNo(), user,
		// getNotificationService());
		setResMsg(MsgUtil.getSuccessMsg("cancel order successfully"));
	}

	private void cancelOrderList(OrderList orderList) {
		orderList.setState(OrderListService.ORDER_STATE_CANCEL_PAID + "," + orderList.getState());
		for (Order order : orderList.getOrders()) {
			order.setState(OrderService.ORDER_STATE_END_FAILED + "," + OrderService.ORDER_STATE_CANCEL_PAID + ","
					+ order.getState());
		}
		getOrderListService().updateAndAddCount(orderList);
	}

}
