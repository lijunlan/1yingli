package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.EXBackingComment;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.BackingCommentService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;


public class GetBackingCommentService extends UMsgService {

	private BackingCommentService backingCommentService;

	public BackingCommentService getBackingCommentService() {
		return backingCommentService;
	}

	public void setBackingCommentService(BackingCommentService backingCommentService) {
		this.backingCommentService = backingCommentService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		User user = getUser();
		int page = getData().getInt("page");
		List<BackingComment> backingCommentList = getBackingCommentService().
				queryListByUserIdAndPage(user.getId(), page);
		ExList send = new ExArrayList();
		for (BackingComment backingComment : backingCommentList) {
			SuperMap map = new SuperMap();
			EXBackingComment.assembleToUser(backingComment, map);
			send.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", getBackingCommentService().querySumByUserId(user.getId()));
		toSend.put("data", send);
		setResMsg(toSend.finishByJson());
	}
}
