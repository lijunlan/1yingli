package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Order;

public class ExOrderUtil {

	public static void assembleOrderToTeacher(SuperMap map, Order o) {
		map.put("orderId", o.getOrderNo());
		map.put("createTime", o.getCreateTime());
		map.put("title", o.getServiceTitle());
		map.put("price", o.getMoney());
		map.put("originPrice", o.getOriginMoney());
		map.put("time", o.getTime());
		map.put("iconUrl", o.getCreateUser().getIconUrl());
		map.put("name", o.getCustomerName());
		map.put("state", o.getState());
		map.put("question", o.getQuestion());
		map.put("phone", o.getCustomerPhone());
		map.put("email", o.getCustomerEmail());
		map.put("userIntroduce", o.getUserIntroduce());
		map.put("selectTimes", o.getSelectTime());
		map.put("okTime", o.getOkTime());
		map.put("contact", o.getCustomerContact());
	}

	public static void assembleOrderToUser(SuperMap map, Order o) {
		map.put("orderId", o.getOrderNo());
		map.put("createTime", o.getCreateTime());
		map.put("title", o.getServiceTitle());
		map.put("price", o.getMoney());
		map.put("originPrice", o.getOriginMoney());
		map.put("time", o.getTime());
		map.put("teacherId", o.getTeacher().getId());
		map.put("teacherName", o.getTeacher().getName());
		map.put("teacherUrl", o.getTeacher().getIconUrl());
		map.put("state", o.getState());
		map.put("question", o.getQuestion());
		map.put("userIntroduce", o.getUserIntroduce());
		map.put("selectTimes", o.getSelectTime());
		map.put("okTime", o.getOkTime());
		map.put("phone", o.getCustomerPhone());
		map.put("email", o.getCustomerEmail());
		map.put("name", o.getCustomerName());
		map.put("contact", o.getCustomerContact());
	}

	public static void assembleOrderToManager(SuperMap map, Order o) {
		map.put("orderId", o.getOrderNo());
		map.put("createTime", o.getCreateTime());
		map.put("title", o.getServiceTitle());
		map.put("price", o.getMoney().toString());
		map.put("originPrice", o.getOriginMoney());
		map.put("distriName", o.getDistributor() != null ? o.getDistributor().getName() : "");
		map.put("time", o.getTime().toString());
		map.put("teacherName", o.getTeacher().getName());
		map.put("teacherUrl", o.getTeacher().getIconUrl());
		map.put("teacherAlipayNo", o.getAlipayNo());
		map.put("customerName", o.getCustomerName());
		map.put("customerPhone", o.getCustomerPhone());
		map.put("customerEmail", o.getCustomerEmail());
		map.put("state", o.getState());
		map.put("question", o.getQuestion());
		map.put("userIntroduce", o.getUserIntroduce());
		map.put("selectTimes", o.getSelectTime());
		map.put("salaryState", o.getSalaryState());
		map.put("okTime", o.getOkTime());
		map.put("weixin", o.getCustomerContact());
	}
}
