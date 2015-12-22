package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

/**
 * 导师接受学员订单
 * 
 * @author sdll18
 *
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
		if (!OrderService.ORDER_STATE_FINISH_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		order.setState(OrderService.ORDER_STATE_TEACHER_ACCEPT + "," + order.getState());
		getOrderService().updateAndSendTimeTask(order);

		NotifyUtil
				.notifyUserOrder(order,
						"尊敬的学员,您好,您的订单(" + order.getOrderNo() + ")已被导师(" + teacher.getName()
								+ ")接受,导师将通过您提供的联系方式与您联系,请及时与导师确认咨询时间哦。",
						order.getCreateUser(), getNotificationService());
		NotifyUtil.notifyTeacher(order,
				"尊敬的导师,您好,您已接受订单(" + order.getOrderNo() + "),请及时与学员确认时间,并在1天内到一英里平台登记咨询时间,超时系统将自动取消订单哦。(学员姓名:"
						+ order.getCustomerName() + ",电话:" + order.getCustomerPhone() + ",邮箱:"
						+ order.getCustomerEmail() + ",微信:" + order.getCustomerContact() + ")",
				getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经接受订单。");
		setResMsg(MsgUtil.getSuccessMsg("accept order successfully"));
	}

}
