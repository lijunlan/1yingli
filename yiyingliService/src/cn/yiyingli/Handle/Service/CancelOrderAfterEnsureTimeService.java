package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class CancelOrderAfterEnsureTimeService extends MsgService {

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
		if (!OrderService.ORDER_STATE_WAIT_SERVICE.equals(state)) {
			setResMsg(MsgUtil.getErrorMsg("order state is not accurate"));
			return;
		}
		order.setState(OrderService.ORDER_STATE_USER_REGRET + "," + order.getState());
		getOrderService().updateAndSendTimeTask(order);

		NotifyUtil
				.notifyUserOrder(
						order.getCustomerPhone(), order.getCustomerEmail(), "尊敬的学员，您与导师(" + order.getTeacher().getName()
								+ ")约定好咨询时间的订单已经申请取消。订单号" + order.getOrderNo() + "，请等待导师同意",
						user, getNotificationService());
		NotifyUtil.notifyTeacher(order.getTeacher().getPhone(),
				order.getTeacher().getEmail(), "尊敬的导师，您已经与学员(" + user.getName() + ")约定好咨询时间的订单(订单号："
						+ order.getOrderNo() + ")，学员申请取消并退款，等待您的确认，请在5天内进行同意或拒绝，超时系统会自动同意退款。",
				order.getTeacher(), getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + ",学员申请取消并退款，等待导师的确认，");
		setResMsg(MsgUtil.getSuccessMsg("cancel order after ensure time successfully"));
	}

}
