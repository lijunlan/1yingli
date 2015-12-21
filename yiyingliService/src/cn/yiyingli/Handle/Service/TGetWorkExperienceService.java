package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class TGetWorkExperienceService extends MsgService {

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
		List<WorkExperience> workExperiences = teacher.getWorkExperiences();
		ExList toSend = new ExArrayList();
		for (WorkExperience we : workExperiences) {
			SuperMap map = new SuperMap();
			map.put("companyName", we.getCompanyName());
			map.put("position", we.getPosition());
			map.put("description", we.getDescription());
			map.put("startTime", we.getStartTime());
			map.put("endTime", we.getEndTime());
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("workExperience", toSend).finishByJson());
	}

}
