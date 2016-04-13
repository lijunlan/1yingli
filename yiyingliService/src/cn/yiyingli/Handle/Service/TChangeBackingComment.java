package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Service.BackingCommentService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeBackingComment extends TMsgService {
	private BackingCommentService backingCommentService;

	public BackingCommentService getBackingCommentService() {
		return backingCommentService;
	}

	public void setBackingCommentService(BackingCommentService backingCommentService) {
		this.backingCommentService = backingCommentService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("backingCommentId");
	}

	@Override
	public void doit() {
		long backingCommentId = getData().getLong("backingCommentId");
		BackingComment backingComment = getBackingCommentService().query(backingCommentId);
		if (backingComment.getTeacher().getId() != getTeacher().getId()) {
			setResMsg(MsgUtil.getErrorMsgByCode("22010"));
			return;
		}
		if (getData().containsKey("display")) {
			int display = getData().getInt("display");
			if (display == 0) {
				backingComment.setDisplay(false);
			} else {
				backingComment.setDisplay(true);
			}
		}
		if (getData().containsKey("top")) {
			int top = getData().getInt("top");
			if (top == 0) {
				backingComment.setWeight((short) 0);
			} else {
				backingComment.setWeight((short) 1);
			}
		}
		getBackingCommentService().update(backingComment);
		setResMsg(MsgUtil.getSuccessMsg("update backingComment Success"));
	}
}
