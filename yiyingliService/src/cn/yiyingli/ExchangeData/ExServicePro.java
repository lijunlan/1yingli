package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.ServicePro;

public class ExServicePro {

	public static void assembleSimpleTalkForTeacher(ServicePro servicePro, SuperMap map) {
		map.put("content", servicePro.getContent());
		map.put("title", servicePro.getTitle());
		map.put("price", servicePro.getPrice());
		map.put("priceTemp", servicePro.getPriceTemp());
		map.put("count", servicePro.getNumber());
		map.put("onsale", servicePro.getOnSale());
		map.put("numeral", servicePro.getNumeral());
		map.put("quantifier", servicePro.getTips());
		map.put("freeTime", servicePro.getFreeTime());
		map.put("kind", servicePro.getKind());
		map.put("onshow", servicePro.getOnShow());
		map.put("state", servicePro.getState());
		map.put("tip", servicePro.getTips());
	}
}
