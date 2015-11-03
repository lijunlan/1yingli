package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.AlipayCancelUtil;

/**
 * 取消订单，仅存在于还没生成支付宝订单的情况下
 * 
 * @author Administrator
 *
 */
public class CancelOrderService extends MsgService {

	private UserMarkService userMarkService;

	private OrderService orderService;

	private NotificationService notificationService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
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
		return getData().containsKey("uid") && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsg("order is not existed"));
			return;
		}
		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsg("this order is not belong to you"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsg("order state is not accurate"));
			return;
		}
		order.setState(OrderService.ORDER_STATE_CANCEL_PAID + "," + order.getState());

		try {
			if (AlipayCancelUtil.cancelOrder("", oid)) {
				order.setState(OrderService.ORDER_STATE_END_FAILED + "," + order.getState());
				getOrderService().update(order);
				NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
						"尊敬的学员，您的订单已经取消。订单号" + order.getOrderNo(), user, getNotificationService());
				setResMsg(MsgUtil.getSuccessMsg("cancel order successfully"));
				return;
			} else {
				getOrderService().update(order);
				NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
						"尊敬的学员，您的订单已经取消。订单号" + order.getOrderNo(), user, getNotificationService());
				setResMsg(MsgUtil.getSuccessMsg("cancel order successfully"));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		setResMsg(MsgUtil.getErrorMsg("cancel order failed"));
	}

}
