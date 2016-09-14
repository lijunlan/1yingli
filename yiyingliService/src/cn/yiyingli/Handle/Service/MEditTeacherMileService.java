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
		return super.checkData() && getData().containsKey("teacherId") && getData().containsKey("mile")
				&& getData().containsKey("kind")
				&& ("add".equals(getData().getString("kind")) || "sub".equals(getData().getString("kind")));
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
		String kind = getData().getString("kind");
		if ("add".equals(kind)) {
			getTeacherService().updateAddMile(teacherId, mile);
			setResMsg(MsgUtil.getSuccessMsg("add teacher's mile successfully"));
		} else {
			boolean result = getTeacherService().updateAddSubMile(teacherId, mile);
			if (result) {
				setResMsg(MsgUtil.getSuccessMsg("sub teacher's mile successfully"));
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("22009"));
			}
		}

	}

}
