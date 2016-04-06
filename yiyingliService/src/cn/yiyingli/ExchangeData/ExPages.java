package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Pages;

public class ExPages {
	public static void assembleSimple(Pages page, SuperMap map) {
		map.put("pagesId", page.getId());
		map.put("createTime", page.getCreateTime());
		map.put("passageCount", page.getPassageCount());
		map.put("serviceProCount", page.getServiceProCount());
		map.put("teacherCount", page.getTeacherCount());
		map.put("description", page.getDescription());
		map.put("weight", page.getWeight());
		map.put("content", page.getContent());
		map.put("contact", page.getContact());
		map.put("email", page.getEmail());
		map.put("mile", (long)page.getMile().floatValue());
		map.put("key", page.getPagesKey());
	}
}
