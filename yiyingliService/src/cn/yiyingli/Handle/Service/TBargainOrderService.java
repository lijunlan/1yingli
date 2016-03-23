package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.TimeTaskUtil;

import java.util.Calendar;

public class TBargainOrderService extends TMsgService {

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
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("price");
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
		if (!(OrderService.ORDER_BARGAINING.equals(state)
				&& order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		Float price = Float.parseFloat((String) getData().get("price"));
		order.setPrice(price);
		order.setState(OrderService.ORDER_BARGAINED_NOT_PAID + "," + order.getState());
		getOrderService().update(order, false);
		TimeTaskUtil.sendTimeTask("change", "order",
				(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 48) +
						"",
				new SuperMap().put("state", order.getState()).put("orderId",
						order.getOrderNo()).finishByJson());
		NotifyUtil.notifyUserOrder(order,
				"尊敬的学员,您好,您的订单(订单号:" + order.getOrderNo() +
						")已经被导师(" + teacher.getName() + ")确定价格,请在48小时内完成支付哦,超时系统将自动取消订单。",
				order.getCreateUser(), getNotificationService());
		NotifyUtil.notifyTeacher(order, "尊敬的导师，您已经为订单号" + order.getOrderNo() + "的订单确定价格," +
				"请通知用户("+ order.getCustomerName() +")在48小时内完成支付哦,超时系统将自动取消订单。",
				getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经确定价格");
		setResMsg(MsgUtil.getSuccessMsg("bargain order successfully"));
	}
}
