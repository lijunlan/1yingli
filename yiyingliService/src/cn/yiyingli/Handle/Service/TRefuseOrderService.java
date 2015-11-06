package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class TRefuseOrderService extends TMsgService {

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
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("refuseReason");
	}

	@Override
	public void doit() {
		super.doit();
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
		if (!OrderService.ORDER_STATE_FINISH_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String refuseReason = (String) getData().get("refuseReason");
		order.setRefuseReason(refuseReason);
		order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
		getOrderService().update(order);

		NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
				"尊敬的学员，抱歉的通知您，订单号为" + order.getOrderNo() + "的订单已经被导师(" + teacher.getName() + ")拒绝，我们会在24小时内为您退款。",
				order.getCreateUser(), getNotificationService());
		NotifyUtil.notifyTeacher(teacher.getPhone(), teacher.getEmail(),
				"尊敬的导师，您已经拒绝订单号为" + order.getOrderNo() + "的订单。学员姓名：" + order.getCustomerName(), teacher,
				getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经拒绝订单.");
		setResMsg(MsgUtil.getSuccessMsg("refuse order successfully"));
	}

}
