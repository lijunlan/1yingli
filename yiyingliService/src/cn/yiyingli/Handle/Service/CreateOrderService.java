package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import cn.yiyingli.Util.TimeTaskUtil;
import cn.yiyingli.toPersistant.POrderUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CreateOrderService extends UMsgService {

	private TeacherService teacherService;

	private OrderService orderService;

	private UserService userService;

	private NotificationService notificationService;

	private VoucherService voucherService;

	private ServiceProService serviceProService;

	private OrderListService orderListService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public OrderListService getOrderListService() {
		return orderListService;
	}

	public void setOrderListService(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("teacherId") && getData().containsKey("serviceList")
				&& getData().containsKey("name") && getData().containsKey("phone") && getData().containsKey("email")
				&& getData().containsKey("contact") || getData().containsKey("voucher");
	}

	public static final String SERVICE_KIND_TALK = "talk";

	public static final String SERVICE_KIND_SERVICEPRO = "servicePro";

	@Override
	public void doit() {
		User user = getUser();
		Teacher teacher = getTeacherService().queryWithUser(Long.valueOf((String) getData().get("teacherId")));
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		if (!teacher.getOnService()) {
			setResMsg(MsgUtil.getErrorMsgByCode("22002"));
			return;
		}
		if (teacher.getUser().getId().longValue() == user.getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsgByCode("44006"));
			return;
		}
		JSONArray jsonServiceList = getData().getJSONArray("serviceList");
		int serviceProSum = jsonServiceList.size();
		long[] serviceProIdstoSql = new long[serviceProSum];
		long[] serviceProIds = new long[serviceProSum];
		for (int i = 0; i < jsonServiceList.size(); i++) {
			JSONObject obj = jsonServiceList.getJSONObject(i);
			if (obj.getString("serviceKind").equals(SERVICE_KIND_SERVICEPRO)) {
				serviceProIdstoSql[i] = obj.getLong(ServiceProService.TAG_ID);
				serviceProIds[i] = obj.getLong(ServiceProService.TAG_ID);
			} else {
				serviceProSum--;
				serviceProIds[i] = -1L;
			}
		}
		List<ServicePro> servicePros = getServiceProService().queryList(serviceProIdstoSql, teacher.getId());
		if (servicePros.size() != serviceProSum) {
			setResMsg(MsgUtil.getErrorMsgByCode("44008"));
			return;
		}

		String phone = (String) getData().get("phone");
		String email = (String) getData().get("email");
		String contact = (String) getData().get("contact");
		String name = (String) getData().get("name");

		if (!(CheckUtil.checkEmail(email)
				&& (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("12008"));
			return;
		}

		long ntime = Calendar.getInstance().getTimeInMillis();
		OrderList orderList = new OrderList();
		orderList.setCreateTime(ntime + "");
		orderList.setState(OrderListService.ORDER_STATE_NOT_PAID);
		orderList.setOriginMoney(0F);
		orderList.setNowMoney(0F);
		orderList.setPayMoney(0F);
		orderList.setShowToTeacher(false);
		orderList.setTeacher(teacher);
		orderList.setUser(user);
		orderList.setCustomerName(name);
		orderList.setCustomerEmail(email);
		orderList.setCustomerWX(contact);
		orderList.setCustomerPhone(phone);
		for (int i = 0; i < serviceProIds.length; i++) {
			JSONObject jsonService = jsonServiceList.getJSONObject(i);
			ServicePro servicePro = null;
			if (serviceProIds[i] != -1L) {
				for (ServicePro sp : servicePros) {
					long spId = sp.getId();
					if (serviceProIds[i] == spId) {
						servicePro = sp;
						break;
					}
				}
			}
			Order order = new Order();
			String question = jsonService.getString("question");
			String resume = jsonService.getString("resume");
			String selectTime = jsonService.getString("selectTime");
			int count = jsonService.getInt("count");
			POrderUtil.createOrder(user, teacher, phone, email, contact, name, question, resume, selectTime, count,
					servicePro, order);
			orderList.setOriginMoney(orderList.getOriginMoney() + order.getOriginMoney());
			orderList.setNowMoney(orderList.getNowMoney() + order.getMoney());
			orderList.getOrders().add(order);
		}

		// boolean isUseVoucher = false;
		Voucher voucher = null;
		float money = orderList.getNowMoney();
		if (getData().containsKey("voucher")) {
			String vno = (String) getData().get("voucher");
			voucher = getVoucherService().query(vno, false);
			if (voucher == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("45001"));
				return;
			} else if (ntime > Long.valueOf(voucher.getEndTime()) || ntime < Long.valueOf(voucher.getStartTime())) {
				setResMsg(MsgUtil.getErrorMsgByCode("45002"));
				return;
			} else if (voucher.getUsed()) {
				setResMsg(MsgUtil.getErrorMsgByCode("45003"));
				return;
			} else {
				money = money - voucher.getMoney();
				voucher.setUsed(true);
				orderList.getUseVouchers().add(voucher);
			}
		}
		if (money < 0.01) {
			money = 0.01F;
		}
		orderList.setPayMoney(money);

		user.setOname(name);
		user.setOphone(phone);
		user.setOemail(email);
		user.setContact(contact);

		getUserService().update(user);

		String r = getOrderListService().saveAndSubCount(orderList);
		if (r.equals(OrderListService.ORDER_ERROR_COUNT_LIMITED)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44009"));
			return;
		}

		for (Order order : orderList.getOrders()) {
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 48) + "",
					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		}
		//
		// if (isUseVoucher) {
		// getVoucherService().updateWithOrderListId(voucher,
		// orderList.getId());
		// }

		SendMsgToBaiduUtil.updateUserTrainDataOrder(user.getId() + "", teacher.getId() + "",
				Calendar.getInstance().getTimeInMillis() + "");

		NotifyUtil.notifyUserOrder(phone, email,
				"尊敬的学员,您好,您的订单组(流水号:" + orderList.getOrderListNo() + ")已经创建,为了能及时预约到心动导师,请在48小时内完成支付哦,超时系统将自动取消订单。",
				user, getNotificationService());

		setResMsg(MsgUtil.getSuccessMap().put("orderNoList", orderList.getOrderListNo())
				.put("msg", "create order successfully").finishByJson());
	}

}
