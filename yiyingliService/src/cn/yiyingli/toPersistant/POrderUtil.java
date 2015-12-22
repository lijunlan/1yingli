package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.StringUtil;

public class POrderUtil {

	public static void createOrder(User user, Teacher teacher, String phone, String email, String contact, String name,
			String question, String resume, String selectTime, int count, ServicePro servicePro, Order order) {
		order.setOnSale(servicePro.getOnSale());
		order.setCustomerEmail(email);
		order.setCustomerName(name);
		order.setCustomerPhone(phone);
		order.setCustomerContact(contact);
		order.setCreateUser(user);
		order.setQuestion(question);
		order.setSelectTime(selectTime);
		order.setServiceTitle(StringUtil.replaceBlank(servicePro.getTitle().trim()));
		order.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		order.setState(OrderService.ORDER_STATE_NOT_PAID);
		order.setTeacher(teacher);
		order.setPaypalNo(teacher.getPaypal());
		order.setAlipayNo(teacher.getAlipay());
		order.setQuantifier(servicePro.getQuantifier());
		order.setNumeral(servicePro.getNumeral());
		order.setCount(count);
		order.setServiceId(servicePro.getId());
		order.setUserIntroduce(resume);
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_OFF);
		order.setDistributor(user.getDistributor());
		order.setMoney(servicePro.getOnSale() ? servicePro.getPrice() * (float) count
				: servicePro.getPriceTemp() * (float) count);
		order.setOriginMoney(servicePro.getPrice() * (float) count);
	}
}
