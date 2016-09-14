package cn.yiyingli.Handle.Service;

import java.util.List;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetStudyExperienceService extends TMsgService {

	@Override
	public void doit() {

		Teacher teacher = getTeacher();

		List<StudyExperience> studyExperiences = teacher.getStudyExperiences();
		ExList toSend = new ExArrayList();
		for (StudyExperience se : studyExperiences) {
			SuperMap map = new SuperMap();
			map.put("schoolName", se.getSchoolName());
			map.put("major", se.getMajor());
			map.put("degree", se.getDegree());
			map.put("description", se.getDescription());
			map.put("startTime", se.getStartTime());
			map.put("endTime", se.getEndTime());
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("studyExperience", toSend).finishByJson());
	}

}
