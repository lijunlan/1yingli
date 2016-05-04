package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

/**
 * 导师接受用户订单
 *
 * @author sdll18
 */
public class TAcceptOrderService extends TMsgService {

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
		if (!((OrderService.ORDER_STATE_FINISH_PAID.equals(state)
				&& (order.getServiceType() == null || order.getServiceType().equals(ServicePro.SERVICE_TYPE_NORMAL)))
				|| (OrderService.ORDER_BARGAINED_NOT_PAID.equals(state)
				&& order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		if (order.getServiceId() != null && order.getServiceKind() != null) {
			boolean needBargain = (ServicePro.SERVICE_TYPE_BARGAIN == order.getServiceType());
			switch (order.getServiceKind()) {
				case ServiceProService.KIND_CONSULTATION:
				case ServiceProService.KIND_EXPERIENCE:
				case ServiceProService.KIND_TEACH:
					dealOrder(order, true, needBargain);
					break;
				case ServiceProService.KIND_MODIFY:
				case ServiceProService.KIND_CUSTOMIZE:
					dealOrder(order, false, needBargain);
					break;
				default:
					dealOrder(order, true, needBargain);
					break;
			}
		} else {
			dealOrder(order, true, false);
		}
		setResMsg(MsgUtil.getSuccessMsg("accept order successfully"));
	}

	public void dealOrder(Order order, boolean needEnsureTime, boolean needBargain) {
		if (needBargain) {
			order.setState(OrderService.ORDER_BARGAINING + "," + order.getState());
		} else {
			if (needEnsureTime) {
				order.setState(OrderService.ORDER_STATE_WAIT_ENSURETIME + "," + order.getState());
			} else {
				order.setState(OrderService.ORDER_STATE_WAIT_SERVICE + "," + order.getState());
			}
		}
		getOrderService().updateAndSendTimeTask(order);
		NotifyUtil.notifyUserOrder(order, getNotificationService());
		NotifyUtil.notifyTeacher(order, getNotificationService());
	}

}
