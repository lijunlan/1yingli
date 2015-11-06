package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Util.MsgUtil;

public class GetCommentCountService extends UMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("kind");
	}

	@Override
	public void doit() {
		User user = getUser();
		String kind = (String) getData().get("kind");
		short k = 0;
		if (String.valueOf(CommentService.COMMENT_KIND_FROMUSER_SHORT).equals(kind)) {
			k = CommentService.COMMENT_KIND_FROMUSER_SHORT;
		} else if (String.valueOf(CommentService.COMMENT_KIND_FROMTEACHER_SHORT).equals(kind)) {
			k = CommentService.COMMENT_KIND_FROMTEACHER_SHORT;
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("12009"));
			return;
		}

		long count = 0;
		int page = 0;
		if (k == CommentService.COMMENT_KIND_FROMTEACHER_SHORT) {
			count = user.getReceiveCommentNumber();
		} else {
			count = user.getSendCommentNumber();
		}
		if (count % CommentService.PAGE_SIZE_INT > 0)
			page = (int) (count / CommentService.PAGE_SIZE_INT) + 1;
		else
			page = (int) (count / CommentService.PAGE_SIZE_INT);
		if (page == 0)
			page = 1;
		setResMsg(MsgUtil.getSuccessMap().put("count", count).put("page", page).finishByJson());
	}

}
