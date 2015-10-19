package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class MDealOrderService extends MsgService {

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
		return getData().containsKey("mid") && getData().containsKey("orderId") && getData().containsKey("deal");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsg("order is not existed"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_MANAGER_IN.equals(state)) {
			setResMsg(MsgUtil.getErrorMsg("order state is not accurate"));
			return;
		}
		String deal = (String) getData().get("deal");
		if (Boolean.valueOf(deal)) {
			// 判断订单时候是服务后进入此状态的
			if (order.getState().contains(OrderService.ORDER_STATE_USER_DISLIKE)) {
				// 状态跳为等待付薪状态
				order.setState(OrderService.ORDER_STATE_WAIT_COMMENT + "," + order.getState());
				order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
				getOrderService().update(order);
				NotifyUtil.notifyUser(order.getCustomerPhone(), order.getCustomerEmail(),
						"尊敬的学员，订单号为" + order.getOrderNo() + "的订单，导师(" + order.getTeacher().getName()
								+ ")，客服已经协调结束，协调结果：拒绝此次退款。请到一英里平台对本次咨询进行评价",
						order.getCreateUser(), getNotificationService());
				NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
						"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，客服已经协调结束，协调结果：拒绝此次退款。您的酬劳会在24小时内到账.",
						order.getTeacher(), getNotificationService());
				NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
						+ order.getTeacher().getName() + "，客服已经协调结束，协调结果：拒绝此次退款。");	
			} else if (order.getState().contains(OrderService.ORDER_STATE_USER_REGRET)) {
				// 状态跳为订单失败，正常情况下钱将打入导师账户，特殊情况下可人工操作进行特殊处理
				order.setState(OrderService.ORDER_STATE_END_FAILED + "," + order.getState());
				order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
				getOrderService().update(order);
				NotifyUtil.notifyUser(order.getCustomerPhone(),
						order.getCustomerEmail(), "尊敬的学员，订单号为" + order.getOrderNo() + "的订单，导师("
								+ order.getTeacher().getName() + ")，客服已经协调结束，协调结果：拒绝此次退款。",
						order.getCreateUser(), getNotificationService());
				NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
						"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，客服已经协调结束，协调结果：拒绝此次退款。", order.getTeacher(),
						getNotificationService());
				NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
						+ order.getTeacher().getName() + "，客服已经协调结束，协调结果：拒绝此次退款。");	
			} else {
				// 出错
				LogUtil.error("order state error when manager deal the order,state:" + order.getState(), getClass());
				setResMsg(MsgUtil.getErrorMsg("order state is wrong"));
				return;
			}
		} else if (!Boolean.valueOf(deal)) {
			order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
			getOrderService().update(order);
			// 退钱,等待将钱退回学员支付宝
			NotifyUtil.notifyUser(order.getCustomerPhone(),
					order.getCustomerEmail(), "尊敬的学员，订单号为" + order.getOrderNo() + "的订单，导师("
							+ order.getTeacher().getName() + ")，客服已经协调结束，协调结果：同意此次退款。我们将在24小时内给您退款.",
					order.getCreateUser(), getNotificationService());
			NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
					"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，客服已经协调结束，协调结果：同意此次退款。", order.getTeacher(),
					getNotificationService());
			NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
					+ order.getTeacher().getName() + "，客服已经协调结束，协调结果：同意此次退款。");	
		} else {
			setResMsg(MsgUtil.getErrorMsg("deal colunm has error data"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMsg("deal successfully"));
	}

}
