package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import cn.yiyingli.ExchangeData.LikeNoShowUtil;
import cn.yiyingli.ExchangeData.ExTeacherSimpleShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.RecordService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

public class GetTeacherAllInfoService extends MsgService {

	private TeacherService teacherService;

	private RecordService recordService;

	private UserMarkService userMarkService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		// getData().containsKey("uid")
		return getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacherService().queryWithTips(Long.valueOf((String) getData().get("teacherId")), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
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

		saveRecord(teacher);

		setResMsg(map.finishByJson());
	}

	/**
	 * 为浏览添加记录
	 * 
	 * @param teacher
	 */
	private void saveRecord(Teacher teacher) {
		Long no = teacher.getLookNumber();
		if (no == null) {
			no = 1L;
		} else {
			no++;
		}
		teacher.setLookNumber(no);
		getTeacherService().update(teacher);

		Record r = new Record();
		r.setKind(RecordService.RECORD_KIND_SEE_TEACHER);
		r.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		r.setIp((String) getData().get("IP"));

		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
					r.setType(RecordService.RECORD_TYPE_TEACHER);
					r.setData("teacherId=" + teacher.getId() + ",userId=" + user.getId());
				} else {
					r.setType(RecordService.RECORD_TYPE_USER);
					r.setData("teacherId=" + teacher.getId() + ",userId=" + user.getId());
				}
				SendMsgToBaiduUtil.updateUserTrainDataLike(user.getId() + "", teacher.getId() + "",
						Calendar.getInstance().getTimeInMillis() + "");
			} else {
				r.setType(RecordService.RECORD_TYPE_GUEST);
				r.setData("teacherId=" + teacher.getId());
			}
		} else {
			r.setType(RecordService.RECORD_TYPE_GUEST);
			r.setData("teacherId=" + teacher.getId());
		}

		getRecordService().save(r);
	}

}
