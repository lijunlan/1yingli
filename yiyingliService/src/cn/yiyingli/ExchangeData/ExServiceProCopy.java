package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import net.sf.json.JSONObject;

public class ExServiceProCopy {

	public static boolean checkServiceAndOrder(Order order, ServicePro servicePro) {
		String json = order.getServiceInfo();
		JSONObject service = JSONObject.fromObject(json);
		if (service.getLong("serviceProId") == servicePro.getId().longValue()) {
			return true;
		}
		return false;
	}

	public static String getServiceProTitle(Order order) {
		String json = order.getServiceInfo();
		JSONObject service = JSONObject.fromObject(json);
		return service.getString(ServiceProService.TAG_TITLE);
	}

	public static String getServiceProMultiTitle(Order order) {
		// String json = order.getServiceInfos();
		// JSONArray services = JSONArray.fromObject(json);
		// return
		// services.getJSONObject(0).getString(ServiceProService.TAG_TITLE)
		// + (services.size() > 1 ? ("等" + services.size() + "项服务") : "");
		return "";
	}
}
