package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.LikeNoShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class GetTeacherSimpleInfoService extends MsgService {

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
		Teacher teacher = getTeacherService().query(Long.valueOf((String) getData().get("teacherId")), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("name", teacher.getName());
		map.put("iconUrl", teacher.getIconUrl());
		map.put("simpleinfo", teacher.getSimpleInfo());
		map.put("level", teacher.getLevel());
		LikeNoShowUtil.setFinishNo(teacher, map);
		LikeNoShowUtil.setLikeNo(teacher, map);
		map.put("teacherId", teacher.getId());
		TService tService = teacher.gettService();
		map.put("timeperweek", tService.getTimesPerWeek());
		// map.put("serviceTitle", tService.getTitle());
		map.put("serviceContent", tService.getContent());

		setResMsg(map.finishByJson());
	}

}
