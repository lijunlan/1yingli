package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.StringUtil;

public class POrderUtil {

	public static void createOrder(User user, Teacher teacher, String phone, String email, String contact, String name,
			String question, String time, String resume, float money, float originMoney, boolean onSale, Order order) {
		order.setOnSale(onSale);
		order.setCustomerEmail(email);
		order.setCustomerName(name);
		order.setCustomerPhone(phone);
		order.setCustomerContact(contact);
		order.setCreateUser(user);
		order.setQuestion(question);
		order.setSelectTime(time);
		order.setServiceTitle(StringUtil.replaceBlank(teacher.gettService().getTitle().trim()));
		order.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		order.setState(OrderService.ORDER_STATE_NOT_PAID);
		order.setTeacher(teacher);
		order.setPaypalNo(teacher.getPaypal());
		order.setAlipayNo(teacher.getAlipay());
		order.setTime(teacher.gettService().getTime());
		order.settService(teacher.gettService());
		order.setUserIntroduce(resume);
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_OFF);
		order.setDistributor(user.getDistributor());
		order.setMoney(money);
		order.setOriginMoney(originMoney);
	}
}
