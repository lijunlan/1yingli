package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;

public class ExOrderUtil {

	public static void assembleOrderToTeacher(SuperMap map, Order o) {
		assembleOrderNormal(map, o);
		map.put("iconUrl", o.getCreateUser().getIconUrl());
		map.put("name", o.getCustomerName());
		map.put("phone", o.getCustomerPhone());
		map.put("email", o.getCustomerEmail());
		map.put("contact", o.getCustomerContact());
	}

	public static void assembleOrderToUser(SuperMap map, Order o) {
		assembleOrderNormal(map, o);
		map.put("teacherId", o.getTeacher().getId());
		map.put("teacherName", o.getTeacher().getName());
		map.put("teacherUrl", o.getTeacher().getIconUrl());
		map.put("phone", o.getCustomerPhone());
		map.put("email", o.getCustomerEmail());
		map.put("name", o.getCustomerName());
		map.put("contact", o.getCustomerContact());
	}

	public static void assembleOrderToManager(SuperMap map, Order o) {
		assembleOrderNormal(map, o);
		if (o.getPayMethod() != null) {
			switch (o.getPayMethod()) {
			case OrderService.ORDER_PAYMETHOD_ALIPAY:
				map.put("payMethod", "alipay");
				break;
			case OrderService.ORDER_PAYMETHOD_PAYPAL:
				map.put("payMethod", "paypal");
				break;
			default:
				map.put("payMethod", o.getPayMethod() + "");
				break;
			}
		} else {
			map.put("payMethod", "none");
		}
		map.put("distriName", o.getDistributor() != null ? o.getDistributor().getName() : "");
		map.put("payTime", o.getPayTime());

		map.put("teacherName", o.getTeacher().getName());
		map.put("teacherUrl", o.getTeacher().getIconUrl());
		map.put("teacherAlipayNo", o.getAlipayNo());
		map.put("teacherPaypalNo", o.getPaypalNo());
		map.put("customerName", o.getCustomerName());
		map.put("customerPhone", o.getCustomerPhone());
		map.put("customerEmail", o.getCustomerEmail());
		map.put("salaryState", o.getSalaryState());
		map.put("weixin", o.getCustomerContact());
	}

	private static void assembleOrderNormal(SuperMap map, Order o) {
		map.put("orderId", o.getOrderNo());
		map.put("onsale", o.getOnSale());
		map.put("createTime", o.getCreateTime());
		map.put("title", o.getServiceTitle());
		map.put("price", o.getMoney());
		map.put("originPrice", o.getOriginMoney());
		map.put("count", o.getCount());
		map.put("quantifier", o.getQuantifier());
		map.put("numeral", o.getNumeral());
		map.put("userIntroduce", o.getUserIntroduce());
		map.put("selectTimes", o.getSelectTime());
		map.put("refuseReason", o.getRefuseReason());
		map.put("state", o.getState());
		map.put("okTime", o.getOkTime());
		map.put("question", o.getQuestion());
		map.put("endTime", o.getEndTime());
	}
}
