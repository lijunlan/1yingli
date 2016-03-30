package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Util.MsgUtil;

public class TGetWorkExperienceService extends TMsgService {

	@Override
	public void doit() {

		Teacher teacher = getTeacher();

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
