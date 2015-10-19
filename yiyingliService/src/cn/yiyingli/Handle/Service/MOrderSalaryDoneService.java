package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class MOrderSalaryDoneService extends MsgService {

	private ManagerMarkService managerMarkService;

	private OrderService orderService;

	private NotificationService notificationService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

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
		return getData().containsKey("orderId") && getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsg("order is not existed"));
			return;
		}
		// String state = order.getState().split(",")[0];
		if (!(order.getSalaryState().shortValue() == OrderService.ORDER_SALARY_STATE_NEED)) {
			setResMsg(MsgUtil.getErrorMsg("order salary state is not accurate"));
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
