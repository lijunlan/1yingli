package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.ServicePro;

public class ExServicePro {

	public static void assembleDetailServiceForUser(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		assembelDetail(servicePro, map);
		map.put("tip", servicePro.getTips());
		map.put("teacherId", servicePro.getTeacher().getId());
		map.put("score", servicePro.getScore());
		LikeAndFinishNoShowUtil.setLikeNo(servicePro, map);
		LikeAndFinishNoShowUtil.setFinishNo(servicePro, map);
	}

	public static void assembleDetailServiceForManager(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		assembelDetail(servicePro, map);
		map.put("onshow", servicePro.getOnShow());
		map.put("tip", servicePro.getTips());
		map.put("score", servicePro.getScore());
		map.put("serviceProState", servicePro.getState());
		LikeAndFinishNoShowUtil.setLikeNo(servicePro, map);
		LikeAndFinishNoShowUtil.setFinishNo(servicePro, map);
	}

	public static void assembleDetailServiceForTeacher(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		assembelDetail(servicePro, map);
		map.put("tip", servicePro.getTips());
		map.put("score", servicePro.getScore());
		map.put("onshow", servicePro.getOnShow());
		map.put("serviceProState", servicePro.getState());
		LikeAndFinishNoShowUtil.setLikeNo(servicePro, map);
		LikeAndFinishNoShowUtil.setFinishNo(servicePro, map);
	}

	public static void assembleSimpleServiceProForManager(ServicePro servicePro, SuperMap map) {
		assembelSimple(servicePro, map);
		map.put("state", servicePro.getState());
		map.put("tip", servicePro.getTips());
		map.put("onshow", servicePro.getOnShow());
		map.put("answerRatio", servicePro.getAnswerRatio());
		map.put("answerTime", servicePro.getAnswerTime());
		map.put("score", servicePro.getScore());
		map.put("teacherName", servicePro.getTeacher().getName());
		map.put("teacherId", servicePro.getTeacher().getId());
		// map.put("teacherSimpleInfo",
		// servicePro.getTeacher().getSimpleInfo());
		map.put("praiseRatio", servicePro.getPraiseRatio());
		LikeAndFinishNoShowUtil.setLikeNo(servicePro, map);
		LikeAndFinishNoShowUtil.setFinishNo(servicePro, map);
	}

	public static void assembleSimpleServiceProForTeacher(ServicePro servicePro, SuperMap map) {
		assembelSimple(servicePro, map);
		map.put("state", servicePro.getState());
		map.put("score", servicePro.getScore());
		map.put("teacherName", servicePro.getTeacher().getName());
		map.put("teacherSimpleInfo", servicePro.getTeacher().getSimpleInfo());
		LikeAndFinishNoShowUtil.setLikeNo(servicePro, map);
		LikeAndFinishNoShowUtil.setFinishNo(servicePro, map);

	}

	public static void assembleSimpleServiceProForUser(ServicePro servicePro, SuperMap map) {
		assembelSimple(servicePro, map);
		// map.put("tip", servicePro.getTips());
		// map.put("answerRatio", servicePro.getAnswerRatio());
		// map.put("answerTime", servicePro.getAnswerTime());
		map.put("score", servicePro.getScore());
		map.put("teacherName", servicePro.getTeacher().getName());
		map.put("teacherSimpleInfo", servicePro.getTeacher().getSimpleInfo());
		LikeAndFinishNoShowUtil.setLikeNo(servicePro, map);
		LikeAndFinishNoShowUtil.setFinishNo(servicePro, map);
		// map.put("praiseRatio", servicePro.getPraiseRatio());
	}

	private static void assembelSimple(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		map.put("imageUrls", servicePro.getImageUrls());
		map.put("serviceProId", servicePro.getId());
		map.put("summary", servicePro.getSummary());
		// map.put("content", servicePro.getContent());
		map.put("title", servicePro.getTitle());
		map.put("price", servicePro.getPrice());
		map.put("priceTemp", servicePro.getPriceTemp());
		map.put("count", servicePro.getNumber());
		map.put("onsale", servicePro.getOnSale());
		map.put("numeral", servicePro.getNumeral());
		map.put("quantifier", servicePro.getQuantifier());
		// map.put("freeTime", servicePro.getFreeTime());
		map.put("kind", servicePro.getKind());
		map.put("type",servicePro.getType());
	}

	private static void assembelDetail(ServicePro servicePro, SuperMap map) {
		// 根据UI修改
		map.put("address", servicePro.getAddress());
		map.put("talkWay", servicePro.getTalkWay());
		map.put("praiseRatio", servicePro.getPraiseRatio());
		map.put("answerRatio", servicePro.getAnswerRatio());
		map.put("answerTime", servicePro.getAnswerTime());
		map.put("serviceProId", servicePro.getId());
		map.put("imageUrls", servicePro.getImageUrls());
		map.put("summary", servicePro.getSummary());
		map.put("content", servicePro.getContent());
		map.put("title", servicePro.getTitle());
		map.put("price", servicePro.getPrice());
		map.put("priceTemp", servicePro.getPriceTemp());
		map.put("count", servicePro.getNumber());
		map.put("onsale", servicePro.getOnSale());
		map.put("numeral", servicePro.getNumeral());
		map.put("quantifier", servicePro.getQuantifier());
		map.put("freeTime", servicePro.getFreeTime());
		map.put("kind", servicePro.getKind());
		map.put("type",servicePro.getType());
	}
}
