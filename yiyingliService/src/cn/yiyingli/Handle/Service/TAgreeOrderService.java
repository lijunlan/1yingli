package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class TAgreeOrderService extends TMsgService {

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
		Teacher teacher = getTeacher();
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		if (order.getTeacher().getId().longValue() != teacher.getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!(OrderService.ORDER_STATE_USER_DISLIKE.equals(state)
				|| OrderService.ORDER_STATE_USER_REGRET.equals(state))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String okTime = (String) getData().get("okTime");
		order.setOkTime(okTime);
		order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
		getOrderService().update(order, false);

		NotifyUtil.notifyUserOrder(order,
				"尊敬的用户，订单号为" + order.getOrderNo() + "的订单，申请退款已经被导师(" + teacher.getName() + ")接受，我们会在24小时内为您退款。",
				order.getCreateUser(), getNotificationService());
		NotifyUtil.notifyTeacher(order, "尊敬的导师，您已经同意订单号为" + order.getOrderNo() + "的退款。", getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经同意订单退款。");
		setResMsg(MsgUtil.getSuccessMsg("agree order successfully"));
	}
}
