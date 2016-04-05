package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.BackingCommentService;
import cn.yiyingli.Service.TeacherService;

import java.util.List;

public class FGetBackingCommentService extends MsgService{

	private TeacherService teacherService;

	private BackingCommentService backingCommentService;

	public BackingCommentService getBackingCommentService() {
		return backingCommentService;
	}

	public void setBackingCommentService(BackingCommentService backingCommentService) {
		this.backingCommentService = backingCommentService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		long teacherId = getData().getLong("teacherId");
		int page = getData().getInt("page");
		List<BackingComment> backingCommentList = getBackingCommentService().queryListByTeacherIdAndPage(teacherId,page);

	}
}
