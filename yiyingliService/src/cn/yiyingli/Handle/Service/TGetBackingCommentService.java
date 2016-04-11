package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.EXBackingComment;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.BackingCommentService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class TGetBackingCommentService extends TMsgService {


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
		return super.checkData();
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		long teacherId = teacher.getId();
		int page = getData().getInt("page");
		List<BackingComment> backingCommentList = getBackingCommentService().queryListByTeacherIdAndPage(teacherId, page);
		ExList send = new ExArrayList();
		for (BackingComment backingComment : backingCommentList) {
			SuperMap map = new SuperMap();
			EXBackingComment.assembleToTeacher(backingComment, map);
			send.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", getBackingCommentService().querySumByTeacherId(teacherId, false));
		toSend.put("data", send);
		setResMsg(toSend.finishByJson());
	}
}
