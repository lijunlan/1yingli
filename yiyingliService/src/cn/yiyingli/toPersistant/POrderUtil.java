package cn.yiyingli.toPersistant;

import java.util.Calendar;
import java.util.Random;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.StringUtil;

public class POrderUtil {

	public static String getOrderNo(long id) {
		return "" + Calendar.getInstance().get(Calendar.YEAR) + new Random().nextInt(10) + new Random().nextInt(10)
				+ new Random().nextInt(10) + (100000000L + id);
	}

	public static void createOrder(User user, Teacher teacher, String phone, String email, String contact, String name,
			String question, String resume, String selectTime, int count, ServicePro servicePro, Order order) {
		if (servicePro != null) {
			order.setServiceKind(servicePro.getKind());
			order.setOnSale(servicePro.getOnSale());
			order.setServiceTitle(StringUtil.replaceBlank(servicePro.getTitle().trim()));
			order.setQuantifier(servicePro.getQuantifier());
			order.setNumeral(servicePro.getNumeral());
			order.setServiceId(servicePro.getId());
			order.setPrice(servicePro.getOnSale() ? servicePro.getPriceTemp() : servicePro.getPrice());
			order.setOriginPrice(servicePro.getPrice());
			order.setMoney(servicePro.getOnSale() ? servicePro.getPriceTemp() * (float) count
					: servicePro.getPrice() * (float) count);
			order.setOriginMoney(servicePro.getPrice() * (float) count);
			order.setServiceSummary(servicePro.getSummary());
			order.setIconUrl(servicePro.getImageUrls().split(",")[0]);
		} else {
			order.setOnSale(false);
			order.setServiceTitle(teacher.getTopic());
			order.setQuantifier("分钟");
			order.setNumeral(10F);
			order.setServiceId(null);
			order.setServiceKind(null);
			order.setPrice(teacher.getPrice());
			order.setOriginPrice(teacher.getPrice());
			float price = teacher.getPrice() * (float) count;
			order.setMoney(price);
			order.setOriginMoney(price);
			order.setServiceSummary(teacher.getSimpleInfo());
			order.setIconUrl(teacher.getIconUrl());
		}
		order.setCustomerEmail(email);
		order.setCustomerName(name);
		order.setCustomerPhone(phone);
		order.setCustomerContact(contact);
		order.setCreateUser(user);
		order.setQuestion(question);
		order.setSelectTime(selectTime);
		order.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		order.setState(OrderService.ORDER_STATE_NOT_PAID);
		order.setTeacher(teacher);
		order.setPaypalNo(teacher.getPaypal());
		order.setAlipayNo(teacher.getAlipay());
		order.setCount(count);
		order.setUserIntroduce(resume);
		order.setSalaryState(OrderService.ORDER_SALARY_STATE_OFF);
		order.setDistributor(user.getDistributor());
		order.setReturnVisit(false);
	}
}
