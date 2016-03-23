package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
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
		if (!((OrderService.ORDER_STATE_FINISH_PAID.equals(state) &&
				order.getServiceType().equals(ServicePro.SERVICE_TYPE_NORMAL))
				|| (OrderService.ORDER_BARGAINED_NOT_PAID.equals(state) &&
				order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN))
				||(OrderService.ORDER_BARGAINING.equals(state) &&
				order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String refuseReason = (String) getData().get("refuseReason");
		order.setRefuseReason(refuseReason);
		boolean needBargain = (ServicePro.SERVICE_TYPE_BARGAIN == order.getServiceType());
		if (needBargain) {
			order.setState(OrderService.ORDER_STATE_END_FAILED + "," +
					OrderService.ORDER_STATE_CANCEL_PAID + "," + order.getState());
		} else {
			order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
		}
		getOrderService().update(order, false);
		if (needBargain) {
			NotifyUtil.notifyUserOrder(
					order, "尊敬的用户,抱歉的通知您,您的订单(" + order.getOrderNo() + ")已被导师(" + teacher.getName() + ")拒绝,拒绝理由:"
							+ refuseReason + ",您可预约其他优秀的导师哦。",
					order.getCreateUser(), getNotificationService());
		} else {
			NotifyUtil.notifyUserOrder(
					order, "尊敬的用户,抱歉的通知您,您的订单(" + order.getOrderNo() + ")已被导师(" + teacher.getName() + ")拒绝,拒绝理由:"
							+ refuseReason + ",您可预约其他优秀的导师哦,预付款将在24小时内退还到您的账户。",
					order.getCreateUser(), getNotificationService());
		}
		NotifyUtil.notifyTeacher(order, "尊敬的导师，您已经拒绝订单号为" + order.getOrderNo() + "的订单。用户姓名：" + order.getCustomerName()
				+ ",拒绝理由:" + refuseReason, getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经拒绝订单.拒绝理由：" + refuseReason);
		setResMsg(MsgUtil.getSuccessMsg("refuse order successfully"));
	}

}
