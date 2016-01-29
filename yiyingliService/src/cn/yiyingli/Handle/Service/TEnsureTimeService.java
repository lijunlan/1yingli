package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class TEnsureTimeService extends TMsgService {

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
		return super.checkData() && getData().containsKey("okTime") && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String oid = getData().getString("orderId");
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
		if (!OrderService.ORDER_STATE_TEACHER_ACCEPT.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String okTime = (String) getData().get("okTime");
		order.setOkTime(okTime);
		order.setState(OrderService.ORDER_STATE_WAIT_SERVICE + "," + order.getState());
		getOrderService().updateAndSendTimeTask(order);

		NotifyUtil.notifyUserOrder(order,
				"尊敬的学员,您好,您已与导师(" + teacher.getName() + ")约好咨询时间(" + okTime + "),请等待咨询,并在2周内确认咨询或取消咨询,系统将在2周后自动确认咨询成功.",
				order.getCreateUser(), getNotificationService());
		NotifyUtil.notifyTeacher(order,
				"尊敬的导师,您好,您与学员(" + order.getCustomerName() + ",电话:" + order.getCustomerPhone() + ",邮箱:"
						+ order.getCustomerEmail() + ",微信:" + order.getCustomerContact() + ")约定好时间,请在" + okTime
						+ "进行咨询.系统会在2周后自动确认咨询成功.",
				getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经与学员约定好时间。" + okTime);
		setResMsg(MsgUtil.getSuccessMsg("accept order successfully"));
	}

}
