package cn.yiyingli.ExchangeData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;

public class ExTeacher {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	public static void assembleSimpleForUserLike(Teacher teacher, SuperMap map) {
		assembleSimpleNormal(teacher, map);
		map.put("score", teacher.getScore());
		LikeAndFinishNoShowUtil.setLikeNo(teacher, map);
		LikeAndFinishNoShowUtil.setFinishNo(teacher, map);
		ExList serviceProList = new ExArrayList();
		for (ServicePro servicePro : teacher.getServicePros()) {
			if (servicePro.getOnShow() && (!servicePro.getRemove())
					&& servicePro.getState() == ServiceProService.STATE_OK) {
				SuperMap m = new SuperMap();
				ExServicePro.assembleSimpleServiceProForUser(servicePro, m);
				serviceProList.add(m.finish());
				if (serviceProList.size() >= ServiceProService.PAGE_SIZE) {
					break;
				}
			}
		}
		map.put("servicePros", serviceProList);
	}

	public static void assembleSimpleForUser(Teacher teacher, SuperMap map) {
		assembleSimpleNormal(teacher, map);
		map.put("level", teacher.getLevel());
		map.put("introduce", teacher.getIntroduce());
		LikeAndFinishNoShowUtil.setLikeNo(teacher, map);
		LikeAndFinishNoShowUtil.setFinishNo(teacher, map);
	}

	private static void assembleSimpleNormal(Teacher teacher, SuperMap map) {
		map.put("name", teacher.getName());
		map.put("teacherId", teacher.getId());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("simpleinfo", teacher.getSimpleInfo());
		map.put("price", teacher.getPrice());
		map.put("topic", teacher.getTopic());
	}

	public static void assembleSimpleForManager(Teacher teacher, SuperMap map) {
		map.put("address", teacher.getAddress());
		map.put("mile", (long)teacher.getMile().floatValue());
		map.put("subMile", teacher.getSubMile());
		map.put("price", teacher.getPrice());
		map.put("topic", teacher.getTopic());
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
		map.put("username", teacher.getUsername());
		map.put("onService", teacher.getOnService());
	}

	private static void assembleDetailNormal(Teacher teacher, SuperMap map) {
		map.put("onChat", teacher.getOnChat());
		map.put("rewardNumber", teacher.getRewardNumber());
		map.put("simpleinfo", teacher.getSimpleInfo());
		map.put("name", teacher.getName());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("bgUrl", teacher.getBgUrl());
		map.put("phone", teacher.getPhone());
		map.put("mile", (long)teacher.getMile().floatValue());
		map.put("email", teacher.getEmail());
		map.put("introduce", teacher.getIntroduce());
		LikeAndFinishNoShowUtil.setLikeNo(teacher, map);
		LikeAndFinishNoShowUtil.setFinishNo(teacher, map);
		map.put("score", teacher.getScore());
		map.put("answerRatio", teacher.getAnswerRatio());
		map.put("praiseRatio", teacher.getPraiseRatio());
		map.put("answerTime", teacher.getAnswerTime());
		map.put("address", teacher.getAddress());
		map.put("teacherId", teacher.getId());
		map.put("commentNo", teacher.getCommentNumber());
		map.put("topic", teacher.getTopic());
		map.put("price", teacher.getPrice());
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
		ExList toSendSe = new ExArrayList();
		for (StudyExperience se : ses) {
			SuperMap m = new SuperMap();
			m.put("schoolName", se.getSchoolName());
			m.put("major", se.getMajor());
			m.put("degree", se.getDegree());
			m.put("description", se.getDescription());
			m.put("startTime", se.getStartTime());
			m.put("endTime", se.getEndTime());
			toSendSe.add(m.finish());
		}
		ExList toSendwe = new ExArrayList();
		for (WorkExperience we : wes) {
			SuperMap m = new SuperMap();
			m.put("companyName", we.getCompanyName());
			m.put("position", we.getPosition());
			m.put("description", we.getDescription());
			m.put("startTime", we.getStartTime());
			m.put("endTime", we.getEndTime());
			toSendwe.add(m.finish());
		}
		ExList toSendtip = new ExArrayList();
		for (Tip t : tips) {
			SuperMap m = new SuperMap();
			m.put("id", t.getId());
			toSendtip.add(m.finish());
		}
		map.put("workExperience", toSendwe);
		map.put("studyExperience", toSendSe);
		map.put("tips", toSendtip);
	}

	public static void assembleDetailForUser(Teacher teacher, SuperMap map) {
		assembleDetailNormal(teacher, map);
	}

	public static void assembleDetailForTeacher(Teacher teacher, SuperMap map) {
		assembleDetailNormal(teacher, map);
		map.put("showNotify", teacher.getShowNotify());
	}

	public static void assembleDetailForManager(Teacher teacher, SuperMap map) {
		assembleDetailNormal(teacher, map);
		map.put("showNotify", teacher.getShowNotify());
		map.put("mile",(long) teacher.getMile().floatValue());
		map.put("alipayNo", teacher.getAlipay());
		map.put("paypalNo", teacher.getPaypal());
		map.put("checkPassageNumber", teacher.getCheckPassageNumber());
		map.put("passageNumber", teacher.getPassageNumber());
		map.put("refusePassageNumber", teacher.getRefusePassageNumber());
		map.put("onService", teacher.getOnService());
	}

}
