package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.ServicePro;

public class ExServicePro {

	public static void assembleDetailServiceForUser(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		assembelDetail(servicePro, map);
		map.put("tip", servicePro.getTips());
		map.put("finishNo", servicePro.getFinishNo());
		map.put("answerRatio", servicePro.getAnswerRatio());
		map.put("answerTime", servicePro.getAnswerTime());
		map.put("score", servicePro.getScore());
		map.put("likeNo", servicePro.getLikeNo());
		map.put("praiseRatio", servicePro.getPraiseRatio());
	}

	public static void assembleDetailServiceForManager(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		assembelDetail(servicePro, map);
		map.put("tip", servicePro.getTips());
		map.put("finishNo", servicePro.getFinishNo());
		map.put("answerRatio", servicePro.getAnswerRatio());
		map.put("answerTime", servicePro.getAnswerTime());
		map.put("score", servicePro.getScore());
		map.put("likeNo", servicePro.getLikeNo());
		map.put("praiseRatio", servicePro.getPraiseRatio());
		map.put("serviceProState", servicePro.getState());
		map.put("saleWeight", servicePro.getSaleWeight());
		map.put("homeWeight", servicePro.getHomeWeight());
	}

	public static void assembleDetailServiceForTeacher(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		assembelDetail(servicePro, map);
		map.put("tip", servicePro.getTips());
		map.put("finishNo", servicePro.getFinishNo());
		map.put("answerRatio", servicePro.getAnswerRatio());
		map.put("answerTime", servicePro.getAnswerTime());
		map.put("score", servicePro.getScore());
		map.put("likeNo", servicePro.getLikeNo());
		map.put("praiseRatio", servicePro.getPraiseRatio());
		map.put("onshow", servicePro.getOnShow());
		map.put("serviceProState", servicePro.getState());
	}

	public static void assembleSimpleServiceForUser(ServicePro servicePro, SuperMap map) {
		assembelSimple(servicePro, map);
		// map.put("tip", servicePro.getTips());
		map.put("finishNo", servicePro.getFinishNo());
		// map.put("answerRatio", servicePro.getAnswerRatio());
		// map.put("answerTime", servicePro.getAnswerTime());
		map.put("score", servicePro.getScore());
		map.put("likeNo", servicePro.getLikeNo());
		map.put("teacherName", servicePro.getTeacher().getName());
		map.put("teacherSimpleInfo", servicePro.getTeacher().getSimpleInfo());
		// map.put("praiseRatio", servicePro.getPraiseRatio());
	}

	private static void assembelSimple(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		map.put("summary", servicePro.getSummary());
		// map.put("content", servicePro.getContent());
		map.put("title", servicePro.getTitle());
		map.put("price", servicePro.getPrice());
		map.put("priceTemp", servicePro.getPriceTemp());
		map.put("count", servicePro.getNumber());
		map.put("onsale", servicePro.getOnSale());
		map.put("numeral", servicePro.getNumeral());
		map.put("quantifier", servicePro.getTips());
		// map.put("freeTime", servicePro.getFreeTime());
		map.put("kind", servicePro.getKind());
	}

	private static void assembelDetail(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
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
	}
}
