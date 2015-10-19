package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.LikeNoShowUtil;
import cn.yiyingli.ExchangeData.SimpleShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class TGetTeacherSimpleInfoService extends MsgService {

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
			setResMsg(MsgUtil.getErrorMsg("teahcerId is not existed"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("iconUrl", teacher.getIconUrl());
		map.put("name", teacher.getName());
		LikeNoShowUtil.setLikeNo(teacher, map);
		SimpleShowUtil.getSimpleShowByTip(teacher, map);
		setResMsg(map.finishByJson());
	}

}
