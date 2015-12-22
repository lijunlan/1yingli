package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class MOrderReturnDoneService extends MMsgService {

	private OrderService orderService;

	private NotificationService notificationService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_WAIT_RETURN.equals(state)) {
			if (OrderService.ORDER_STATE_END_FAILED.equals(state)) {
				setResMsg(MsgUtil.getErrorMsgByCode("45005"));
				return;
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("44002"));
				return;
			}
		}
		order.setState(OrderService.ORDER_STATE_END_FAILED + "," + OrderService.ORDER_STATE_RETURN_SUCCESS + ","
				+ order.getState());
		getOrderService().update(order, false);

		NotifyUtil.notifyUserOrder(order, "尊敬的学员,您的订单(" + order.getOrderNo() + ")已经成功退款,请注意查收.如有疑问请咨询客服.",
				order.getCreateUser(), getNotificationService());
		setResMsg(MsgUtil.getSuccessMsg("update order state successfully"));
	}

}
