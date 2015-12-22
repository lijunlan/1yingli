package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;
import net.sf.json.JSONObject;

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
		map.put("teacherId", o.getTeacherId());
		map.put("teacherName", ExTeacherCopy.getTeacherName(o));
		map.put("teacherUrl", ExTeacherCopy.getTeacherIconUrl(o));
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

		map.put("teacherName", ExTeacherCopy.getTeacherName(o));
		map.put("teacherUrl", ExTeacherCopy.getTeacherIconUrl(o));
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
		map.put("onSale", o.getOnSale());
		map.put("createTime", o.getCreateTime());
		String jsonServiceInfo = o.getServiceInfo();
		
		JSONObject jsonService = JSONObject.fromObject(jsonServiceInfo);
		
		JSONObject obj = new JSONObject();
		obj.put("title", jsonService.getString("title"));
		obj.put("quantifier", jsonService.getString("quantifier"));
		obj.put("price", jsonService.getDouble("price"));
		obj.put("priceTemp", jsonService.getDouble("priceTemp"));
		obj.put("numeral", jsonService.getInt("numeral"));
		obj.put("onSale", jsonService.getBoolean("onSale"));
		obj.put("count", jsonService.getInt("count"));
		obj.put("kind", jsonService.getInt("kind"));
		obj.put("userIntroduce", jsonService.getString("userIntroduce"));
		obj.put("question", jsonService.getString("question"));
		obj.put("selectTimes", jsonService.getString("selectTimes"));
		obj.put("okTime", jsonService.getString("okTime"));
		obj.put("onSale", jsonService.getString("onSale"));

		map.put("services", obj.toString());

		map.put("price", o.getMoney());
		map.put("originPrice", o.getOriginMoney());
		map.put("refuseReason", o.getRefuseReason());
		map.put("state", o.getState());
		map.put("endTime", o.getEndTime());
	}
}
