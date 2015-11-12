package cn.yiyingli.ExchangeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;

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

	public static void assembleDetailTeacher(Teacher teacher, SuperMap map) {
		map.put("name", teacher.getName());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("bgUrl", teacher.getBgUrl());
		ExTeacherSimpleShowUtil.getSimpleShowByTip(teacher, map);
		map.put("phone", teacher.getPhone());
		map.put("email", teacher.getEmail());
		map.put("introduce", teacher.getIntroduce());
		LikeNoShowUtil.setLikeNo(teacher, map);
		map.put("address", teacher.getAddress());
		map.put("talkWay", teacher.getTalkWay());
		map.put("teacherId", teacher.getId());
		map.put("commentNo", teacher.getCommentNumber());
		TService tService = teacher.gettService();
		map.put("timeperweek", tService.getTimesPerWeek());
		map.put("freeTime", tService.getFreeTime());
		map.put("price", tService.getPriceTotal());
		map.put("serviceTime", tService.getTime());
		// map.put("serviceTitle", tService.getTitle());
		map.put("serviceContent", tService.getContent());
		map.put("checkEmail", teacher.getCheckEmail());
		map.put("checkPhone", teacher.getCheckPhone());
		if (teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT)
			map.put("checkDegree", true);
		else
			map.put("checkDegree", false);
		if (teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT)
			map.put("checkIDCard", true);
		else
			map.put("checkIDCard", false);
		if (teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT)
			map.put("checkWork", true);
		else
			map.put("checkWork", false);
		List<WorkExperience> wes = teacher.getWorkExperiences();
		List<StudyExperience> ses = teacher.getStudyExperiences();
		Set<Tip> tips = teacher.getTips();
		List<String> toSendSe = new ArrayList<String>();
		for (StudyExperience se : ses) {
			SuperMap m = new SuperMap();
			m.put("schoolName", se.getSchoolName());
			m.put("major", se.getMajor());
			m.put("degree", se.getDegree());
			m.put("description", se.getDescription());
			m.put("startTime", se.getStartTime());
			m.put("endTime", se.getEndTime());
			toSendSe.add(m.finishByJson());
		}
		List<String> toSendwe = new ArrayList<String>();
		for (WorkExperience we : wes) {
			SuperMap m = new SuperMap();
			m.put("companyName", we.getCompanyName());
			m.put("position", we.getPosition());
			m.put("description", we.getDescription());
			m.put("startTime", we.getStartTime());
			m.put("endTime", we.getEndTime());
			toSendwe.add(m.finishByJson());
		}
		List<String> toSendtip = new ArrayList<String>();
		for (Tip t : tips) {
			SuperMap m = new SuperMap();
			m.put("id", t.getId());
			toSendtip.add(m.finishByJson());
		}
		map.put("workExperience", Json.getJson(toSendwe));
		map.put("studyExperience", Json.getJson(toSendSe));
		map.put("tips", Json.getJson(toSendtip));
	}
}
