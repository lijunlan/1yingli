package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

public class CreateOrderService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private OrderService orderService;

	private UserService userService;

	private NotificationService notificationService;

	private VoucherService voucherService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

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

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("question")
				&& getData().containsKey("userIntroduce") && getData().containsKey("teacherId")
				&& getData().containsKey("selectTime") && getData().containsKey("name")
				&& getData().containsKey("phone") && getData().containsKey("email") && getData().containsKey("contact")
				|| getData().containsKey("voucher");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Teacher teacher = getTeacherService().query(Long.valueOf((String) getData().get("teacherId")), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("teacher is not existed"));
			return;
		}
		if (!teacher.getOnService()) {
			setResMsg(MsgUtil.getErrorMsg("teacher is not on service"));
			return;
		}
		String phone = (String) getData().get("phone");
		String email = (String) getData().get("email");
		String contact = (String) getData().get("contact");
		String name = (String) getData().get("name");
		String question = (String) getData().get("question");
		String time = (String) getData().get("selectTime");
		String resume = (String) getData().get("userIntroduce");

		phone = CheckUtil.getCorrectPhone(phone);
		if (!(CheckUtil.checkEmail(email) && CheckUtil.checkGlobleMobileNumber(phone))) {
			setResMsg(MsgUtil.getErrorMsg("BAD PHONE NUMBER OR BAD EMAIL"));
			return;
		}
		Order order = new Order();
		order.setCustomerEmail(email);
		order.setCustomerName(name);
		order.setCustomerPhone(phone);
		order.setCustomerContact(contact);
		order.setCreateUser(user);

		order.setQuestion(question);
		order.setSelectTime(time);
		order.setServiceTitle(teacher.gettService().getTitle());
		order.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		order.setState(OrderService.ORDER_STATE_NOT_PAID);
		order.setTeacher(teacher);
		order.setAlipayNo(teacher.getAlipay());
		order.setTime(teacher.gettService().getTime());
		order.settService(teacher.gettService());
		order.setUserIntroduce(resume);
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_OFF);

		user.setResume(resume);
		user.setOname(name);
		user.setOphone(phone);
		user.setOemail(email);
		user.setOquestion(question);
		user.setOtime(time);
		user.setContact(contact);

		getUserService().update(user);

		float money = teacher.gettService().getPriceTotal();
		float originMoney = teacher.gettService().getPriceTotal();
		long ntime = Calendar.getInstance().getTimeInMillis();
		Voucher voucher = null;
		if (getData().containsKey("voucher")) {
			String vno = (String) getData().get("voucher");
			voucher = getVoucherService().query(vno, false);
			if (voucher == null) {
				//
				setResMsg(MsgUtil.getErrorMsg("voucher is not existed"));
				return;
			} else if (ntime > Long.valueOf(voucher.getEndTime()) || ntime < Long.valueOf(voucher.getStartTime())) {
				//
				setResMsg(MsgUtil.getErrorMsg("voucher is overdue"));
				return;
			} else if (voucher.getUsed()) {
				setResMsg(MsgUtil.getErrorMsg("voucher has been used"));
				return;
			} else {
				money = money - voucher.getMoney();
				voucher.setUsed(true);
				voucher.setUseOrder(order);
			}
		}
		if (money < 0.01)
			money = 0.01F;
		order.setMoney(money);
		order.setOriginMoney(originMoney);
		getOrderService().save(order);
		if (voucher != null) {
			getVoucherService().update(voucher);
		}

		SendMsgToBaiduUtil.updateUserTrainDataOrder(user.getId() + "", teacher.getId() + "",
				Calendar.getInstance().getTimeInMillis() + "");

		NotifyUtil.notifyUser(phone, email, "尊敬的学员，您的导师预约订单已经创建。订单号" + order.getOrderNo() + "，请在48小时内完成支付，超时系统会自动取消订单。",
				user, getNotificationService());

		setResMsg(MsgUtil.getSuccessMsg("create order successfully"));
	}

}
