package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.CheckNoService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Service.VoucherService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMailUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import cn.yiyingli.Util.TimeTaskUtil;

/**
 * 用户在没有登陆的情况下生成订单
 * 
 * @author lp
 *
 */
public class CreateOrderWithoutLoginService extends MsgService {

	private String extensionInformation;

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private OrderService orderService;

	private UserService userService;

	private NotificationService notificationService;

	private VoucherService voucherService;

	private CheckNoService checkNoService;

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

	public CheckNoService getCheckNoService() {
		return checkNoService;
	}

	public void setCheckNoService(CheckNoService checkNoService) {
		this.checkNoService = checkNoService;
	}

	@Override
	protected boolean checkData() {
		// TODO Auto-generated method stub
		return getData().containsKey("question") && getData().containsKey("userIntroduce")
				&& getData().containsKey("checkNo") && getData().containsKey("teacherId")
				&& getData().containsKey("selectTime") && getData().containsKey("name")
				&& getData().containsKey("email") && getData().containsKey("contact")
				|| getData().containsKey("voucher");
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

		extensionInformation = "";
		String email = (String) getData().get("email");
		String contact = (String) getData().get("contact");
		String name = (String) getData().get("name");
		String checkNo = (String) getData().get("checkNo");

		Teacher teacher = getTeacherService().query(Long.valueOf((String) getData().get("teacherId")), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("teacher is not existed"));
			return;
		}
		if (!teacher.getOnService()) {
			setResMsg(MsgUtil.getErrorMsg("teacher is not on service"));
			return;
		}
		CheckNo no = getCheckNoService().query(email);
		long time = Calendar.getInstance().getTimeInMillis();
		if (no == null) {
			setResMsg(MsgUtil.getErrorMsg("this phone or email have no checkNo"));
			return;
		}
		if (!(no.getCheckNo().equals(checkNo))) {
			setResMsg(MsgUtil.getErrorMsg("checkNo is wrong"));
			return;
		} else if (time > Long.valueOf(no.getEndTime())) {
			setResMsg(MsgUtil.getErrorMsg("checkNo is overdue"));
			getCheckNoService().remove(no);
			return;
		} else {
			getCheckNoService().remove(no);
		}
		// 1. 检查用户是否已经注册，先检查邮箱
		if (!(CheckUtil.checkEmail(email))) {
			// CheckUtil.checkMobileNumber(phone) &&PHONE NUMBER OR BAD
			setResMsg(MsgUtil.getErrorMsg("BAD  EMAIL"));
			return;
		}
		User user = getUserService().query(email, false);
		if (user != null) {
			// 邮箱有注册
			createOrder(email, name, "", contact, user, teacher);
		} else {
			// 从来没注册过
			user = createUser(email, "");
			LogUtil.info("create a user and username is " + email, this.getClass());
			createOrder(email, name, "", contact, user, teacher);
		}
	}

	public void createOrder(String email, String name, String phone, String contact, User user, Teacher teacher) {
		Order order = new Order();
		order.setCustomerEmail(email);
		order.setCustomerName(name);
		order.setCustomerPhone(phone);
		order.setCustomerContact(contact);
		order.setCreateUser(user);

		order.setQuestion((String) getData().get("question"));
		order.setSelectTime((String) getData().get("selectTime"));
		order.setServiceTitle(teacher.gettService().getTitle());
		order.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		order.setState(OrderService.ORDER_STATE_NOT_PAID);
		order.setTeacher(teacher);
		order.setAlipayNo(teacher.getAlipay());
		order.setTime(teacher.gettService().getTime());
		order.settService(teacher.gettService());
		order.setUserIntroduce((String) getData().get("userIntroduce"));
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_OFF);

		user.setResume((String) getData().get("userIntroduce"));
		getUserService().update(user);

		float money = teacher.gettService().getPriceTotal();
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
		String orderNo = getOrderService().save(order);
		if (voucher != null) {
			getVoucherService().update(voucher);
		}

		SendMsgToBaiduUtil.updateUserTrainDataOrder(user.getId() + "", teacher.getId() + "",
				Calendar.getInstance().getTimeInMillis() + "");

		SendMailUtil.sendMessage(email,
				"尊敬的学员，您的导师预约订单已经创建。订单号" + order.getOrderNo() + "，请在24小时内完成支付，超时系统会自动取消订单。" + extensionInformation);

		String _UUID = UUID.randomUUID().toString();
		getUserMarkService().save(String.valueOf(user.getId()), _UUID);

		TimeTaskUtil.sendTimeTask("remove", "userMark", (Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 5) + "",
				_UUID);

		setResMsg(MsgUtil.getSuccessMap().put("uid", _UUID).put("orderId", orderNo).finishByJson());
	}

	private User createUser(String email, String phone) {
		String pw = createPassword();
		User user = new User();
		user.setLikeTeacherNumber(0L);
		user.setOrderNumber(0L);
		// 默认把邮箱地址当做username来注册
		user.setUsername(email);
		user.setNickName("学员_" + email);
		user.setReceiveCommentNumber(0L);
		user.setSendCommentNumber(0L);
		user.setPhone(phone);
		user.setEmail(email);
		user.setPassword(MD5Util.MD5(pw));
		user.setCreateTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		user.setTeacherState(UserService.TEACHER_STATE_OFF_SHORT);
		user.setForbid(false);
		try {
			getUserService().save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		extensionInformation += "由于您预约了导师，但是当前的手机和邮箱并未注册，因此我们帮您创建了账户。账户名：" + email + ",账户密码：" + pw + "。请您妥善保管，并及时修改密码。";
		return user;
	}

	private String createPassword() {
		String pw = "";
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < 4; i++) {
			pw += (char) (r.nextInt(26) + 97);
			pw += r.nextInt(10);
		}
		return pw;
	}
}
