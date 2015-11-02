package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class MRestartOrderService extends MsgService {

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
		return getData().containsKey("mid") && getData().containsKey("orderId");
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
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_WAIT_RETURN.equals(state)) {
			setResMsg(MsgUtil.getErrorMsg("order state is not accurate"));
			return;
		}
		String[] ss = order.getState().split(",");
		if (ss.length < 2) {
			setResMsg(MsgUtil.getErrorMsg("the state of this can not be changed. last state is needed"));
		}
		order.setState(ss[1] + "," + order.getState());
		getOrderService().update(order);
		NotifyUtil.notifyUser(order.getCustomerPhone(), order.getCustomerEmail(),
				"尊敬的学员，订单号为" + order.getOrderNo() + "的订单，订单状态已经被管理员恢复，您可以继续流程.", order.getCreateUser(),
				getNotificationService());
		NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
				"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，订单状态已经被管理员恢复，您可以继续流程.", order.getTeacher(),
				getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + ",订单状态已经被管理员(" + manager.getName() + ")恢复");
	}

}
