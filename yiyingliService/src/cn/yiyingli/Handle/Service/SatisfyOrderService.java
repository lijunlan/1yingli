package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class SatisfyOrderService extends UMsgService {

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
		if (!OrderService.ORDER_STATE_SERVICE_FINISH.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}

		order.setState(OrderService.ORDER_STATE_WAIT_COMMENT + "," + order.getState());
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
		order.setEndTime(Calendar.getInstance().getTimeInMillis() + "");
		getOrderService().update(order, true);

		NotifyUtil.notifyUserOrder(order,
				"尊敬的用户,您好,您已经确认本次服务(订单号:" + order.getOrderNo() + "),相信您与导师合作得很愉快,请在一英里平台对本次服务进行评价哦,谢谢。", user,
				getNotificationService());
		NotifyUtil.notifyTeacher(order,
				"尊敬的导师,您好,(订单号:" + order.getOrderNo() + ")用户已经确认服务,感谢您的付出,您的酬劳将在24小时内到账,请注意查收。",
				getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，用户已经确认服务。");

		setResMsg(MsgUtil.getSuccessMsg("satisfy order successfully"));
	}

}
