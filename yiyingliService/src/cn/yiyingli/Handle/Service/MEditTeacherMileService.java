package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class MEditTeacherMileService extends MMsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("teacherId") && getData().containsKey("mile");
	}

	@Override
	public void doit() {
		long teacherId = Long.valueOf((String) getData().get("teacherId"));
		Teacher teacher = getTeacherService().query(teacherId);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32015"));
			return;
		}
		long mile = Long.valueOf((String) getData().get("mile"));
		teacher.setMile(mile);
		getTeacherService().update(teacher,false);
		setResMsg(MsgUtil.getSuccessMsg("change teacher's mile successfully"));

	}

}
