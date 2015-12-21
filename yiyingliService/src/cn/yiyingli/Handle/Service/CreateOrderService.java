package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import cn.yiyingli.toPersistant.POrderUtil;

public class CreateOrderService extends UMsgService {

	private TeacherService teacherService;

	private OrderService orderService;

	private UserService userService;

	private NotificationService notificationService;

	private VoucherService voucherService;

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
		return super.checkData() && getData().containsKey("question") && getData().containsKey("userIntroduce")
				&& getData().containsKey("teacherId") && getData().containsKey("selectTime")
				&& getData().containsKey("name") && getData().containsKey("phone") && getData().containsKey("email")
				&& getData().containsKey("contact") || getData().containsKey("voucher");
	}

	@Override
	public void doit() {
		User user = getUser();
		Teacher teacher = getTeacherService().queryWithUser(Long.valueOf((String) getData().get("teacherId")), false);
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
		String phone = (String) getData().get("phone");
		String email = (String) getData().get("email");
		String contact = (String) getData().get("contact");
		String name = (String) getData().get("name");
		String question = (String) getData().get("question");
		String time = (String) getData().get("selectTime");
		String resume = (String) getData().get("userIntroduce");

		if (!(CheckUtil.checkEmail(email)
				&& (CheckUtil.checkMobileNumber(phone) || CheckUtil.checkGlobleMobileNumber(phone)))) {
			setResMsg(MsgUtil.getErrorMsgByCode("12008"));
			return;
		}

		user.setResume(resume);
		user.setOname(name);
		user.setOphone(phone);
		user.setOemail(email);
		user.setOquestion(question);
		user.setOtime(time);
		user.setContact(contact);

		getUserService().update(user);

		TService tService = teacher.gettService();

		boolean onSale = tService.getOnSale();
		float money = 0F;
		float originMoney = 0F;
		if (onSale) {
			originMoney = money = tService.getPriceTemp();
		} else {
			originMoney = money = tService.getPriceTotal();
		}
		long ntime = Calendar.getInstance().getTimeInMillis();
		boolean isUseVoucher = false;
		Voucher voucher = null;
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
				isUseVoucher = true;
			}
		}
		if (money < 0.01) {
			money = 0.01F;
		}
		Order order = new Order();
		POrderUtil.createOrder(user, teacher, phone, email, contact, name, question, time, resume, money, originMoney,
				onSale, order);
		String orderNo = getOrderService().save(order);
		if (isUseVoucher) {
			getVoucherService().updateWithOrderId(voucher, order.getId());
		}

		SendMsgToBaiduUtil.updateUserTrainDataOrder(user.getId() + "", teacher.getId() + "",
				Calendar.getInstance().getTimeInMillis() + "");

		NotifyUtil.notifyUserOrder(phone, email,
				"尊敬的学员,您好,导师预约订单(订单号" + order.getOrderNo() + ")已经创建,为了能及时预约到心动导师,请在48小时内完成支付哦,超时系统将自动取消订单。", user,
				getNotificationService());

		setResMsg(
				MsgUtil.getSuccessMap().put("orderNo", orderNo).put("msg", "create order successfully").finishByJson());
	}

}
