package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class MDealOrderService extends MMsgService {

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
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("deal");
	}

	@Override
	public void doit() {
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_MANAGER_IN.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String deal = (String) getData().get("deal");
		if (Boolean.valueOf(deal)) {
			// 判断订单时候是服务后进入此状态的
			if (order.getState().contains(OrderService.ORDER_STATE_USER_DISLIKE)) {
				// 状态跳为等待付薪状态
				order.setState(OrderService.ORDER_STATE_WAIT_COMMENT + "," + order.getState());
				order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
				getOrderService().update(order, true);
				NotifyUtil.notifyUserOrder(order, getNotificationService());
				NotifyUtil.notifyTeacher(order, getNotificationService());
			} else if (order.getState().contains(OrderService.ORDER_STATE_USER_REGRET)) {
				// 状态跳为订单失败，正常情况下钱将打入导师账户，特殊情况下可人工操作进行特殊处理
				order.setState(OrderService.ORDER_STATE_END_FAILED + "," + order.getState());
				order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
				getOrderService().update(order, false);
				NotifyUtil.notifyUserOrder(order, getNotificationService());
				NotifyUtil.notifyTeacher(order, getNotificationService());
			} else {
				// 出错
				LogUtil.error("order state error when manager deal the order,state:" + order.getState(), getClass());
				setResMsg(MsgUtil.getErrorMsgByCode("44002"));
				return;
			}
		} else if (!Boolean.valueOf(deal)) {
			order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
			getOrderService().update(order, false);
			// 退钱,等待将钱退回用户支付宝
			NotifyUtil.notifyUserOrder(order, getNotificationService());
			NotifyUtil.notifyTeacher(order, getNotificationService());
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("44003"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMsg("deal successfully"));
	}

}
