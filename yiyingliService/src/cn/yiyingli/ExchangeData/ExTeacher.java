package cn.yiyingli.ExchangeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExTeacher {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	public static SuperMap assembleSimpleForUser(Teacher teacher) {
		SuperMap map = new SuperMap();
		map.put("name", teacher.getName());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("simpleinfo", teacher.getSimpleInfo());
		map.put("level", teacher.getLevel());
		LikeNoShowUtil.setLikeNo(teacher, map);
		LikeNoShowUtil.setFinishNo(teacher, map);
		map.put("teacherId", teacher.getId());
		TService tService = teacher.gettService();
		map.put("timeperweek", tService.getTimesPerWeek());
		map.put("serviceTitle", tService.getTitle());
		map.put("serviceContent", tService.getContent());
		return map;
	}

	public static void assembleSimpleForManager(Teacher teacher, SuperMap map) {
		map.put("address", teacher.getAddress());
		map.put("mile", teacher.getMile());
		map.put("checkDegree",
				teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "yes" : "no");
		map.put("checkEmail", teacher.getCheckEmail());
		map.put("checkIDCard",
				teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "yes" : "no");
		map.put("checkPhone", teacher.getCheckPhone());
		map.put("checkWork", teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "yes" : "no");
		map.put("createTime", teacher.getCreateTime() == null ? ""
				: SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(teacher.getCreateTime()))));
		map.put("email", teacher.getEmail());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("introduce", teacher.getIntroduce());
		map.put("level", teacher.getLevel());
		map.put("name", teacher.getName());
		map.put("phone", teacher.getPhone());
		map.put("tid", teacher.getId());
		// map.put(key, value)
		map.put("onService", teacher.getOnService());
	}

	public static void assembleDetailForManagerThroughJSONObject(Teacher teacher, JSONObject t) {
		t.put("name", teacher.getName());
		t.put("phone", teacher.getPhone());
		t.put("address", teacher.getAddress());
		t.put("email", teacher.getEmail());
		t.put("iconUrl", teacher.getIconUrl());
		t.put("introduce", teacher.getIntroduce());
		t.put("mile", teacher.getMile());
		t.put("simpleinfo", teacher.getSimpleInfo());
		t.put("showWeight1", teacher.getShowWeight1());
		t.put("showWeight2", teacher.getShowWeight2());
		t.put("showWeight4", teacher.getShowWeight4());
		t.put("showWeight8", teacher.getShowWeight8());
		t.put("showWeight16", teacher.getShowWeight16());
		t.put("homeWeight", teacher.getHomeWeight());
		t.put("saleWeight", teacher.getSaleWeight());
		t.put("checkIDCard",
				teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
		t.put("checkStudy",
				teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
		t.put("checkWork", teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
		t.put("checkPhone", teacher.getCheckPhone() + "");
		t.put("checkEmail", teacher.getCheckEmail() + "");

		TService tService = teacher.gettService();
		t.put("serviceTitle", tService.getTitle());
		t.put("serviceTime", tService.getTime() + "");
		t.put("serviceOnsale", tService.getOnSale());
		t.put("servicePricetemp", tService.getPriceTemp());
		t.put("servicePrice", tService.getPriceTotal() + "");
		t.put("serviceTimePerWeek", tService.getTimesPerWeek() + "");
		t.put("serviceContent", tService.getContent());

		JSONArray tips = new JSONArray();
		for (Tip tip : teacher.getTips()) {
			JSONObject temp = new JSONObject();
			temp.put("id", tip.getId());
			tips.add(temp);
		}
		t.put("tips", tips);

		JSONArray workExp = new JSONArray();
		for (WorkExperience we : teacher.getWorkExperiences()) {
			JSONObject temp = new JSONObject();
			temp.put("companyName", we.getCompanyName());
			temp.put("position", we.getPosition());
			temp.put("startTime", we.getStartTime());
			temp.put("endTime", we.getEndTime());
			temp.put("description", we.getDescription());
			workExp.add(temp);
		}
		t.put("workExperience", workExp);

		JSONArray studyExp = new JSONArray();
		for (StudyExperience se : teacher.getStudyExperiences()) {
			JSONObject temp = new JSONObject();
			temp.put("schoolName", se.getSchoolName());
			temp.put("major", se.getMajor());
			temp.put("degree", se.getDegree());
			temp.put("description", se.getDescription());
			temp.put("startTime", se.getStartTime());
			temp.put("endTime", se.getEndTime());
			studyExp.add(temp);
		}
		t.put("studyExperience", studyExp);
	}

	private static void assembleDetailNormal(Teacher teacher, SuperMap map) {
		map.put("name", teacher.getName());
		map.put("iconUrl", teacher.getIconUrl());
		ExTeacherSimpleShowUtil.getSimpleShowByTip(teacher, map);
		map.put("phone", teacher.getPhone());
		map.put("mile", teacher.getMile());
		map.put("email", teacher.getEmail());
		map.put("introduce", teacher.getIntroduce());
		LikeNoShowUtil.setLikeNo(teacher, map);
		LikeNoShowUtil.setFinishNo(teacher, map);
		map.put("address", teacher.getAddress());
		map.put("talkWay", teacher.getTalkWay());
		map.put("teacherId", teacher.getId());
		map.put("commentNo", teacher.getCommentNumber());
		TService tService = teacher.gettService();
		map.put("timeperweek", tService.getTimesPerWeek());
		map.put("freeTime", tService.getFreeTime());
		map.put("price", tService.getPriceTotal());
		map.put("onsale", tService.getOnSale());
		map.put("pricetemp", tService.getPriceTemp());
		map.put("serviceTime", tService.getTime());
		map.put("serviceTitle", tService.getTitle());
		map.put("serviceContent", tService.getContent());
		map.put("checkEmail", teacher.getCheckEmail());
		map.put("checkPhone", teacher.getCheckPhone());
		if (teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT) {
			map.put("checkDegree", true);
		} else {
			map.put("checkDegree", false);
		}
		if (teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT) {
			map.put("checkIDCard", true);
		} else {
			map.put("checkIDCard", false);
		}
		if (teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT) {
			map.put("checkWork", true);
		} else {
			map.put("checkWork", false);
		}
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

	public static void assembleDetailForUser(Teacher teacher, SuperMap map) {
		assembleDetailNormal(teacher, map);
		map.put("bgUrl", teacher.getBgUrl());
	}

	public static void assembleDetailForManager(Teacher teacher, SuperMap map) {
		assembleDetailNormal(teacher, map);
		map.put("simpleinfo", teacher.getSimpleInfo());
		map.put("mile", teacher.getMile());
		map.put("showWeight1", teacher.getShowWeight1());
		map.put("showWeight2", teacher.getShowWeight2());
		map.put("showWeight4", teacher.getShowWeight4());
		map.put("showWeight8", teacher.getShowWeight8());
		map.put("showWeight16", teacher.getShowWeight16());
		map.put("homeWeight", teacher.getHomeWeight());
		map.put("saleWeight", teacher.getSaleWeight());
		map.put("checkPassageNumber", teacher.getCheckPassageNumber());
		map.put("passageNumber", teacher.getPassageNumber());
		map.put("refusePassageNumber", teacher.getRefusePassageNumber());
	}

}
