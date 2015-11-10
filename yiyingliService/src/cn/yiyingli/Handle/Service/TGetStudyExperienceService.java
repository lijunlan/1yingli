package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class TGetStudyExperienceService extends MsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		String teacherId = (String) getData().get("teacherId");
		Teacher teacher = getTeacherService().query(Long.valueOf(teacherId), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		List<StudyExperience> studyExperiences = teacher.getStudyExperiences();
		List<String> toSend = new ArrayList<String>();
		for (StudyExperience se : studyExperiences) {
			SuperMap map = new SuperMap();
			map.put("schoolName", se.getSchoolName());
			map.put("major", se.getMajor());
			map.put("degree", se.getDegree());
			map.put("description", se.getDescription());
			map.put("startTime", se.getStartTime());
			map.put("endTime", se.getEndTime());
			toSend.add(map.finishByJson());
		}
		setResMsg(MsgUtil.getSuccessMap().put("studyExperience", Json.getJson(toSend)).finishByJson());
	}

}
