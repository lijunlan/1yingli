package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.PassageLookUser;
import cn.yiyingli.Service.*;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import net.sf.json.JSONObject;

import java.util.Calendar;

public class FTimeTaskCallbackService extends MsgService {

	private OrderService orderService;

	private OrderListService orderListService;

	private UserMarkService userMarkService;

	private NotificationService notificationService;

	private PassageService passageService;

	private UserService userService;

	private TeacherService teacherService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("kind") && getData().containsKey("action") && getData().containsKey("data");
	}

	@Override
	public void doit() {
		String kind = (String) getData().get("kind");
		String action = (String) getData().get("action");
		if ("order".equals(kind)) {
			if ("change".equals(action)) {
				String data = (String) getData().get("data");
				JSONObject oMap = JSONObject.fromObject(data);
				Order order = getOrderService().queryByShowId(oMap.getString("orderId"), false);
				if (order == null) {
					setResMsg(MsgUtil.getSuccessMsg("time task done"));
					return;
				}
				String state = order.getState();
				String instate = oMap.getString("state");
				if (!state.equals(instate)) {
					setResMsg(MsgUtil.getSuccessMsg("time task done"));
					return;
				}
				switch (state.split(",")[0]) {
					case OrderService.ORDER_STATE_NOT_PAID:
						// order.setState(OrderService.ORDER_STATE_CANCEL_PAID + ","
						// + order.getState());
						// getOrderService().update(order, false);
						// NotifyUtil.notifyUserOrder(order,
						// "尊敬的用户,您好,由于您超时未支付订单(" + order.getOrderNo() +
						// "),系统已自动关闭,如若需要,请重新创建订单",
						// order.getCreateUser(), getNotificationService());
						break;
					case OrderService.ORDER_BARGAINING:
//					order.setState(OrderService.ORDER_STATE_END_FAILED + ","
//							+ OrderService.ORDER_STATE_CANCEL_PAID + "," + order.getState());
//					 getOrderService().update(order, false);
//					 NotifyUtil.notifyUserOrder(order,
//					 "尊敬的用户,您好,您的订单(" + order.getOrderNo() + ")由于导师("
//					 + order.getTeacher().getName()
//					 + ")未与您约定好咨询价格,系统已自动关闭订单",
//					 order.getCreateUser(), getNotificationService());
//					 NotifyUtil.notifyTeacher(order,
//					 "尊敬的导师,您好,您的订单(" + order.getOrderNo() +
//					 ")由于未及时与用户约定好咨询价格,系统已自动关闭订单.",
//					 getNotificationService());
//					 NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户："
//					 + order.getCustomerName() + ",导师："
//					 + order.getTeacher().getName() +
//					 ",由于导师未及时与用户约定好咨询价格，已经自动进入关闭订单");
						break;
					case OrderService.ORDER_BARGAINED_NOT_PAID:
						order.setState(OrderService.ORDER_STATE_END_FAILED + ","
								+ OrderService.ORDER_STATE_CANCEL_PAID + "," + order.getState());
						order.setState(OrderService.ORDER_STATE_CANCEL_PAID + ","
								+ order.getState());
						getOrderService().update(order, false);
						NotifyUtil.notifyUserOrder(order, getNotificationService());
						NotifyUtil.notifyTeacher(order, getNotificationService());
						break;
					case OrderService.ORDER_STATE_FINISH_PAID:
						// order.setState(OrderService.ORDER_STATE_WAIT_RETURN + ","
						// + order.getState());
						// getOrderService().update(order, false);
						// NotifyUtil.notifyUserOrder(order,
						// "尊敬的用户,您好,订单" + order.getOrderNo() + ",由于导师(" +
						// order.getTeacher().getName()
						// + ")超时未响应,系统已自动进入退款状态,我们将在24小时内为您退款",
						// order.getCreateUser(), getNotificationService());
						// NotifyUtil.notifyTeacher(order,
						// "尊敬的导师,您好,您的订单(" + order.getOrderNo() +
						// ")由于超时未作出响应，系统已自动进入退款状态,如有疑问,请与我们的客服联系.",
						// getNotificationService());
						// NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户："
						// + order.getCustomerName() + ",导师："
						// + order.getTeacher().getName() +
						// ",由于导师超时未作出响应，已经自动进入退款状态");
						break;
					case OrderService.ORDER_STATE_WAIT_ENSURETIME:
						// order.setState(OrderService.ORDER_STATE_WAIT_RETURN + ","
						// + order.getState());
						// getOrderService().update(order, false);
						// NotifyUtil.notifyUserOrder(order,
						// "尊敬的用户,您好,您的订单(" + order.getOrderNo() + ")由于未在24小时内与导师("
						// + order.getTeacher().getName()
						// + ")约定好咨询时间,系统已自动进入退款状态,预付款项将在24小时内返还到您的账户,请注意查收.",
						// order.getCreateUser(), getNotificationService());
						// NotifyUtil.notifyTeacher(order,
						// "尊敬的导师,您好,您的订单(" + order.getOrderNo() +
						// ")由于未在24小时内与用户约定好咨询时间,系统已自动进入退款状态.",
						// getNotificationService());
						// NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户："
						// + order.getCustomerName() + ",导师："
						// + order.getTeacher().getName() +
						// ",由于导师在24小内没有与用户约定好咨询时间，已经自动进入退款状态");
						break;
					case OrderService.ORDER_STATE_WAIT_SERVICE:
						order.setState(OrderService.ORDER_STATE_SERVICE_FINISH + "," + order.getState());
						getOrderService().update(order, false);
						NotifyUtil.notifyUserOrder(order, getNotificationService());
						NotifyUtil.notifyTeacher(order, getNotificationService());
						break;
					case OrderService.ORDER_STATE_USER_DISLIKE:
						order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
						getOrderService().update(order, false);
						NotifyUtil.notifyUserOrder(order, getNotificationService());
						NotifyUtil.notifyTeacher(order, getNotificationService());
						break;
					case OrderService.ORDER_STATE_USER_REGRET:
						order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + order.getState());
						getOrderService().update(order, false);
						NotifyUtil.notifyUserOrder(order, getNotificationService());
						NotifyUtil.notifyTeacher(order, getNotificationService());
						break;
					case OrderService.ORDER_STATE_SERVICE_FINISH:
						order.setState(OrderService.ORDER_STATE_WAIT_COMMENT + "," + order.getState());
						order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
						getOrderService().update(order, true);
						NotifyUtil.notifyUserOrder(order, getNotificationService());
						break;
					default:
						break;
				}
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
				return;
			} else {
				// TODO
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
				return;
			}
		} else if ("userMark".equals(kind)) {
			if ("remove".equals(action)) {
				String data = (String) getData().get("data");
				getUserMarkService().remove(data);
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
				return;
			} else {
				// TODO
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
				return;
			}
		} else if ("orderList".equals(kind)) {
			if ("change".equals(action)) {
				String data = (String) getData().get("data");
				JSONObject oMap = JSONObject.fromObject(data);
				OrderList orderList = getOrderListService().queryByOrderListNo(oMap.getString("orderListId"));
				if (orderList == null) {
					setResMsg(MsgUtil.getSuccessMsg("time task done"));
					return;
				}
				String state = orderList.getState();
				String instate = oMap.getString("state");
				if (!state.equals(instate)) {
					setResMsg(MsgUtil.getSuccessMsg("time task done"));
					return;
				}
				switch (state.split(",")[0]) {
					case OrderListService.ORDER_STATE_NOT_PAID:
						cancelOrderList(orderList);
						NotifyUtil.notifyUserOrder(orderList, getNotificationService());
						break;
					default:
						break;
				}
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
			} else {
				// TODO
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
			}
		} else if ("passage".equals(kind)) {
			if ("add".equals(action)) {
				String data = getData().getString("data");
				JSONObject oMap = JSONObject.fromObject(data);
				Long passageId = oMap.getLong("passageId");
				getPassageService().updateAddLookNumber(passageId, oMap.getLong("number"));
				if (oMap.containsKey("userId")) {
					Long userId = oMap.getLong("userId");
					if (!getPassageService().checkUserLook(passageId, userId)) {
						PassageLookUser passageLookUser = new PassageLookUser();
						passageLookUser.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
						passageLookUser.setUser(getUserService().query(userId, false));
						Passage passage = getPassageService().query(passageId);
						passageLookUser.setPassage(passage);
						getPassageService().save(passageLookUser);
						getTeacherService().updateAddMile(passage.getOwnTeacher().getId(), 0.1F);
					}
				}
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
			} else {
				// TODO
				setResMsg(MsgUtil.getSuccessMsg("time task done"));
			}
		} else {
			setResMsg(MsgUtil.getSuccessMsg("time task done"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMsg("time task done"));
	}

	private void cancelOrderList(OrderList orderList) {
		orderList.setState(OrderListService.ORDER_STATE_CANCEL_PAID + "," + orderList.getState());
		for (Order order : orderList.getOrders()) {
			order.setState(OrderService.ORDER_STATE_END_FAILED + "," + OrderService.ORDER_STATE_CANCEL_PAID + ","
					+ order.getState());
		}
		getOrderListService().updateAndAddCount(orderList);
	}

}
