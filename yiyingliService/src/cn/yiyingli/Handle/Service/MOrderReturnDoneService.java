package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class MOrderReturnDoneService extends MsgService {

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
		setResMsg(MsgUtil
				.getErrorMsg("please go to Alipay to return money and the state will change when return successfully"));
		// String mid = (String) getData().get("mid");
		// Manager manager = getManagerMarkService().queryManager(mid);
		// if (manager == null) {
		// setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
		// return;
		// }
		// String oid = (String) getData().get("orderId");
		// Order order = getOrderService().queryByShowId(oid, false);
		// if (order == null) {
		// setResMsg(MsgUtil.getErrorMsg("order is not existed"));
		// return;
		// }
		// String state = order.getState().split(",")[0];
		// if (!OrderService.ORDER_STATE_WAIT_RETURN.equals(state)) {
		// setResMsg(MsgUtil.getErrorMsg("order state is not accurate"));
		// return;
		// }
		// order.setState(OrderService.ORDER_STATE_END_FAILED + "," +
		// OrderService.ORDER_STATE_RETURN_SUCCESS + ","
		// + order.getState());
		// getOrderService().update(order);
		//
		// NotifyUtil.notifyUser(order.getCustomerPhone(),
		// order.getCustomerEmail(),
		// "尊敬的学员，订单号为" + order.getOrderNo() +
		// "的订单，已经成功退款，请注意查收.如有疑问请咨询lijunlan@1yingli.cn.",
		// order.getCreateUser(), getNotificationService());
		// setResMsg(MsgUtil.getSuccessMsg("update order state successfully"));
	}

}
