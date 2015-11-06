package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class MOrderSalaryDoneService extends MMsgService {

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
		super.doit();
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		// String state = order.getState().split(",")[0];
		if (!(order.getSalaryState().shortValue() == OrderService.ORDER_SALARY_STATE_NEED)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44004"));
			return;
		}
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_DONE);
		getOrderService().update(order);

		NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getPhone(),
				"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，酬劳已经成功转出，请注意查收.如有疑问请咨询lijunlan@1yingli.cn.",
				order.getTeacher(), getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，酬劳已经成功转出。");
		setResMsg(MsgUtil.getSuccessMsg("update order state successfully"));
	}

}
