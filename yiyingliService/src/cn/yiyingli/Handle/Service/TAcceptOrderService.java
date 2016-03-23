package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

/**
 * 导师接受用户订单
 *
 * @author sdll18
 */
public class TAcceptOrderService extends TMsgService {

	private OrderService orderService;

	private NotificationService notificationService;

	private ServiceProService serviceProService;

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

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
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
		if (!(OrderService.ORDER_STATE_FINISH_PAID.equals(state)
				|| OrderService.ORDER_BARGAINED_NOT_PAID.equals(state))) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		if (order.getServiceId() != null && order.getServiceKind() != null) {
			boolean needBargain = (ServicePro.SERVICE_TYPE_BARGAIN == getServiceProService().
					query(order.getServiceId(), false).getType());
			switch (order.getServiceKind()) {
				case ServiceProService.KIND_CONSULTATION:
				case ServiceProService.KIND_EXPERIENCE:
				case ServiceProService.KIND_TEACH:
					dealOrder(order, true, needBargain);
					break;
				case ServiceProService.KIND_MODIFY:
				case ServiceProService.KIND_CUSTOMIZE:
					dealOrder(order, false, needBargain);
					break;
				default:
					dealOrder(order, true, needBargain);
					break;
			}
		} else {
			dealOrder(order, true, false);
		}
		setResMsg(MsgUtil.getSuccessMsg("accept order successfully"));
	}

	public void dealOrder(Order order, boolean needEnsureTime, boolean needBargain) {
		String message2Teacher = null;
		String message2User = null;
		if(needBargain) {
			order.setState(OrderService.ORDER_BARGAINING + "," + order.getState());
			message2Teacher = "尊敬的导师,您好,您已接受订单(" + order.getOrderNo()
					+ "),请及时与用户联系,并到一英里平台登记价格,超时系统将自动取消订单哦。(用户姓名:" + order.getCustomerName() + ",电话:"
					+ order.getCustomerPhone() + ",邮箱:" + order.getCustomerEmail() + ",微信:" + order.getCustomerContact()
					+ ")";
			message2User = "尊敬的用户,您好,您的订单(" + order.getOrderNo() + ")已被导师(" + getTeacher().getName()
					+ ")接受,导师将通过您提供的联系方式与您联系,请及时与导师确认服务价格哦。";
		} else {
			if (needEnsureTime) {
				order.setState(OrderService.ORDER_STATE_WAIT_ENSURETIME + "," + order.getState());
				message2Teacher = "尊敬的导师,您好,您已接受订单(" + order.getOrderNo()
						+ "),请及时与用户确认时间,并到一英里平台登记服务时间,超时系统将自动取消订单哦。(用户姓名:" + order.getCustomerName() + ",电话:"
						+ order.getCustomerPhone() + ",邮箱:" + order.getCustomerEmail() + ",微信:" + order.getCustomerContact()
						+ ")";
				message2User = "尊敬的用户,您好,您的订单(" + order.getOrderNo() + ")已被导师(" + getTeacher().getName()
						+ ")接受,导师将通过您提供的联系方式与您联系,请及时与导师确认服务时间哦。";
			} else {
				order.setState(OrderService.ORDER_STATE_WAIT_SERVICE + "," + order.getState());
				message2Teacher = "尊敬的导师,您好,您已经接受用户(" + order.getCustomerName() + ",电话:" + order.getCustomerPhone() + ",邮箱:"
						+ order.getCustomerEmail() + ",微信:" + order.getCustomerContact() + ")的订单(" + order.getOrderNo()
						+ "),系统会在2周后自动确认服务完毕.";
				message2User = "尊敬的用户,您好,您的订单(" + order.getOrderNo() + ")已被导师(" + getTeacher().getName()
						+ ")接受,导师将通过您提供的联系方式与您联系,请等待服务。";
			}
		}
		getOrderService().updateAndSendTimeTask(order);

		NotifyUtil.notifyUserOrder(order, message2User, order.getCreateUser(), getNotificationService());
		NotifyUtil.notifyTeacher(order, message2Teacher, getNotificationService());
		NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",用户：" + order.getCustomerName() + ",导师："
				+ order.getTeacher().getName() + "，导师已经接受订单。");
	}

}
