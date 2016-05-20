package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
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
import cn.yiyingli.Util.*;
import cn.yiyingli.toPersistant.POrderUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.mapping.Array;

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
		return super.checkData() && getData().containsKey("serviceList")
				&& getData().containsKey("name") && getData().containsKey("phone") && getData().containsKey("email")
				&& getData().containsKey("contact"); //|| getData().containsKey("voucher");
	}

	public static final String SERVICE_KIND_TALK = "talk";

	public static final String SERVICE_KIND_SERVICEPRO = "servicePro";

	@Override
	public void doit() {
		User user = getUser();

		String phone = (String) getData().get("phone");
		String email = (String) getData().get("email");
		String contact = (String) getData().get("contact");
		String name = (String) getData().get("name");

		user.setOname(name);
		user.setOphone(phone);
		user.setOemail(email);
		user.setContact(contact);
		getUserService().update(user);

		if (!(CheckUtil.checkEmail(email)
				&& (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("12008"));
			return;
		}

		JSONArray jsonServiceList = getData().getJSONArray("serviceList");
		int serviceProSum = jsonServiceList.size();
		List<Long> serviceProIds = new ArrayList<>();
		List<Long> talkTeacherIds = new ArrayList<>();
		for (int i = 0; i < jsonServiceList.size(); i++) {
			JSONObject obj = jsonServiceList.getJSONObject(i);
			if (obj.getString("serviceKind").equals(SERVICE_KIND_SERVICEPRO)) {
				serviceProIds.add(obj.getLong(ServiceProService.TAG_ID));
			}
		}

		List<ServicePro> servicePros = getServiceProService().queryListByIds(serviceProIds);
		if (servicePros.size() != serviceProIds.size()) {
			setResMsg(MsgUtil.getErrorMsgByCode("44008"));
			return;
		}
		List<Order> orders = new ArrayList<>();
		for (int i = 0; i < jsonServiceList.size(); i++) {
			JSONObject jsonService = jsonServiceList.getJSONObject(i);
			ServicePro servicePro = null;
			Teacher teacher = null;
			if (jsonService.getString("serviceKind").equals(SERVICE_KIND_SERVICEPRO)) {
				for (ServicePro sp : servicePros) {
					long spId = sp.getId();
					if (jsonService.getLong(ServiceProService.TAG_ID) == spId) {
						servicePro = sp;
						break;
					}
				}
				teacher = servicePro.getTeacher();
				if (teacher == null) {
					setResMsg(MsgUtil.getErrorMsgByCode("22001"));
					return;
				}
			} else {
				teacher = getTeacherService().query(jsonService.getLong("teacherId"));
				if (teacher == null) {
					setResMsg(MsgUtil.getErrorMsgByCode("22001"));
					return;
				}
				if (!teacher.getOnChat()) {
					setResMsg(MsgUtil.getErrorMsgByCode("44010"));
					return;
				}
			}

			if (!teacher.getOnService()) {
				setResMsg(MsgUtil.getErrorMsgByCode("22002"));
				return;
			}
			if (teacher.getUser().getId().longValue() == user.getId().longValue()) {
				setResMsg(MsgUtil.getErrorMsgByCode("44006"));
				return;
			}
			Order order = new Order();
			String question = jsonService.getString("question");
			String resume = jsonService.getString("resume");
			String selectTime = jsonService.getString("selectTime");
			int count = jsonService.getInt("count");
			POrderUtil.createOrder(user, teacher, phone, email, contact, name, question, resume, selectTime, count,
					servicePro, order);
			orders.add(order);
		}
		ExList toSend = new ExArrayList();
		for (Order order : orders) {
			getOrderService().save(order);
			order.setOrderNo("" + Calendar.getInstance().get(Calendar.YEAR)
					+ String.valueOf((Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1000)).substring(1)
					+ (100000000L + order.getId()));
			getOrderService().update(order);
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 48) +
							"",
					new SuperMap().put("state", order.getState()).put("orderId",
							order.getOrderNo()).finishByJson());
			Teacher teacher = order.getTeacher();
			getTeacherService().updateUserLike(teacher, user);
			SendMsgToBaiduUtil.updateUserTrainDataOrder(user.getId() + "", teacher.getId() + "",
					Calendar.getInstance().getTimeInMillis() + "");
			SuperMap map = new SuperMap();
			NotifyUtil.notifyTeacher(order, getNotificationService());
			map.put("orderId", order.getOrderNo());
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}

}
