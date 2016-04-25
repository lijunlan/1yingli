package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.EXBackingComment;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Service.BackingCommentService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class FGetBackingCommentService extends MsgService {

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
		List<BackingComment> backingCommentList = getBackingCommentService().queryListByTeacherIdAndPage(teacherId, page);
		ExList send = new ExArrayList();
		for (BackingComment backingComment : backingCommentList) {
			SuperMap map = new SuperMap();
			EXBackingComment.assembleSimple(backingComment, map);
			send.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", getBackingCommentService().querySumByTeacherId(teacherId, true));
		toSend.put("data", send);
		setResMsg(toSend.finishByJson());
	}
}
