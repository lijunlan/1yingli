package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class IsLikeTeacherService extends UMsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		User user = getUser();
		String teacherId = (String) getData().get("teacherId");
		Teacher teacher = getTeacherService().query(Long.valueOf(teacherId), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		if (getTeacherService().queryCheckLikeUser(teacher.getId(), user.getId())) {
			setResMsg(MsgUtil.getSuccessMap().put("likeTeacher", true).finishByJson());
		} else {
			setResMsg(MsgUtil.getSuccessMap().put("likeTeacher", false).finishByJson());
		}
	}
}
