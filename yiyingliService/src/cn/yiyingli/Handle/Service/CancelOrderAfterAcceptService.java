package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class CancelOrderAfterAcceptService extends UMsgService {

	private OrderService orderService;

	private NotificationService notificationService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("orderId");
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
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
		if (!((OrderService.ORDER_STATE_WAIT_ENSURETIME.equals(state) &&
				order.getServiceType().equals(ServicePro.SERVICE_TYPE_NORMAL))
				|| (OrderService.ORDER_BARGAINING.equals(state) &&
				order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		if (order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)) {
			order.setState(OrderService.ORDER_STATE_END_FAILED + "," +
					OrderService.ORDER_STATE_CANCEL_PAID + "," + order.getState());
		} else {
			order.setState(OrderService.ORDER_STATE_USER_REGRET + "," + order.getState());
		}
		getOrderService().updateAndSendTimeTask(order);
		NotifyUtil.notifyUserOrder(order, getNotificationService());
		NotifyUtil.notifyTeacher(order, getNotificationService());
		setResMsg(MsgUtil.getSuccessMsg("cancel order after accept successfully"));
	}

}
