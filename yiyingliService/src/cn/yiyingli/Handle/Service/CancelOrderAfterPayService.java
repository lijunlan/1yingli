package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class CancelOrderAfterPayService extends UMsgService {

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
		User user = getUser();
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_FINISH_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
		getOrderService().update(order);

		NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
				"尊敬的学员，您已付款的订单已经取消。订单号" + order.getOrderNo() + "，我们会在24小时内为您退款", user, getNotificationService());
		NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
				"尊敬的导师，订单号为" + order.getOrderNo() + "的订单已经被学员取消。", order.getTeacher(), getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + ",订单已经被学员取消，");
		setResMsg(MsgUtil.getSuccessMsg("cancel order after paid successfully"));
	}

}