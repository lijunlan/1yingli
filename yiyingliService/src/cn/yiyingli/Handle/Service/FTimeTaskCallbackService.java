package cn.yiyingli.Handle.Service;

import java.util.Map;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class FTimeTaskCallbackService extends MsgService {

	private OrderService orderService;

	private UserMarkService userMarkService;

	private NotificationService notificationService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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
				Map<String, String> oMap = Json.getMap(data);
				Order order = getOrderService().queryByShowId(oMap.get("orderId"), false);
				if (order == null) {
					setResMsg(MsgUtil.getSuccessMsg("time task done"));
					return;
				}
				String state = order.getState();
				String instate = oMap.get("state");
				if (!state.equals(instate)) {
					setResMsg(MsgUtil.getSuccessMsg("time task done"));
					return;
				}
				switch (state.split(",")[0]) {
				case OrderService.ORDER_STATE_NOT_PAID:
					order.setState(OrderService.ORDER_STATE_CANCEL_PAID + "," + state);
					getOrderService().update(order);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号" + order.getOrderNo() + "，由于您超时未支付，已经自动关闭", order.getCreateUser(),
							getNotificationService());
					break;
				case OrderService.ORDER_STATE_FINISH_PAID:
					order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + state);
					getOrderService().update(order);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号" + order.getOrderNo() + "，由于导师(" + order.getTeacher().getName()
									+ ")超时没有响应，已经自动进入退款状态，我们将在24内为您退款",
							order.getCreateUser(), getNotificationService());
					NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
							"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，由于您超时未作出响应，已经自动进入退款状态", order.getTeacher(),
							getNotificationService());
					NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
							+ order.getTeacher().getName() + ",由于导师超时未作出响应，已经自动进入退款状态");
					break;
				case OrderService.ORDER_STATE_TEACHER_ACCEPT:
					order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + state);
					getOrderService().update(order);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号" + order.getOrderNo() + "，由于导师(" + order.getTeacher().getName()
									+ ")跟你没有在24小时内约定好咨询时间，已经自动进入退款状态，我们将在24内为您退款。",
							order.getCreateUser(), getNotificationService());
					NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
							"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，由于您在24小内没有与学员约定好咨询时间，已经自动进入退款状态",
							order.getTeacher(), getNotificationService());
					NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
							+ order.getTeacher().getName() + ",由于导师在24小内没有与学员约定好咨询时间，已经自动进入退款状态");
					break;
				case OrderService.ORDER_STATE_WAIT_SERVICE:
					order.setState(OrderService.ORDER_STATE_WAIT_COMMENT + "," + state);
					order.setSalaryState(OrderService.ORDER_SALARY_STATE_NEED);
					getOrderService().update(order);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号" + order.getOrderNo() + "，已经自动确认咨询完毕。请到一英里平台对本次咨询进行评价", order.getCreateUser(),
							getNotificationService());
					NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
							"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，已经自动确认咨询完毕，您的酬劳会在24小时内到账。", order.getTeacher(),
							getNotificationService());
					NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
							+ order.getTeacher().getName() + ",已经自动确认咨询完毕");
					break;
				case OrderService.ORDER_STATE_USER_DISLIKE:
					order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + state);
					getOrderService().update(order);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号" + order.getOrderNo() + "，由于导师未作出响应，已经自动确认为同意退款，我们将在24内为您退款。",
							order.getCreateUser(), getNotificationService());
					NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
							"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，由于您未作出响应，已经自动确认为同意退款。", order.getTeacher(),
							getNotificationService());
					NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
							+ order.getTeacher().getName() + "，由于导师未作出响应，已经自动确认为同意退款。");
					break;
				case OrderService.ORDER_STATE_USER_REGRET:
					order.setState(OrderService.ORDER_STATE_WAIT_RETURN + "," + state);
					getOrderService().update(order);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号" + order.getOrderNo() + "，由于导师未作出响应，已经自动确认为同意退款，我们将在24内为您退款。",
							order.getCreateUser(), getNotificationService());
					NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
							"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，由于您未作出响应，已经自动确认为同意退款。", order.getTeacher(),
							getNotificationService());
					NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
							+ order.getTeacher().getName() + "，由于导师未作出响应，已经自动确认为同意退款。");
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
		} else {
			setResMsg(MsgUtil.getSuccessMsg("time task done"));
			return;
		}
	}

}
