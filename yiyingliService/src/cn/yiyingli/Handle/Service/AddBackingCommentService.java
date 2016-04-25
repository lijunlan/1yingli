package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

import java.util.Calendar;

public class AddBackingCommentService extends UMsgService {
	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("content") && getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		User user = getUser();
		Teacher teacher = getTeacherService().query(Long.parseLong((String) getData().get("teacherId")));
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		short weight = 0;
		BackingComment backingComment = new BackingComment();
		backingComment.setContent((String) getData().get("content"));
		backingComment.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		backingComment.setUser(user);
		backingComment.setTeacher(teacher);
		backingComment.setWeight(weight);
		backingComment.setDisplay(false);
		teacher.getBackingComments().add(backingComment);
		getTeacherService().save(teacher);
		setResMsg(MsgUtil.getSuccessMsg("backingComment successfully"));
	}
}
