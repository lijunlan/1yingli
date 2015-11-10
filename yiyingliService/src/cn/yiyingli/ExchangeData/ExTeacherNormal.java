package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;

public class ExTeacherNormal {

	public static SuperMap assembleSimpleTeacher(Teacher teacher) {
		SuperMap map = new SuperMap();
		map.put("name", teacher.getName());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("simpleinfo", teacher.getSimpleInfo());
		map.put("level", teacher.getLevel());
		LikeNoShowUtil.setLikeNo(teacher, map);
		map.put("teacherId", teacher.getId());
		TService tService = teacher.gettService();
		map.put("timeperweek", tService.getTimesPerWeek());
		map.put("serviceTitle", tService.getTitle());
		map.put("serviceContent", tService.getContent());
		return map;
	}
}
