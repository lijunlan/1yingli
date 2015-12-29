package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Util.MsgUtil;

public class TGetCommentCountService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();

		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = teacher.getCommentNumber();
		if (count % CommentService.PAGE_SIZE_INT > 0)
			page = (int) (count / CommentService.PAGE_SIZE_INT) + 1;
		else
			page = (int) (count / CommentService.PAGE_SIZE_INT);
		if (page == 0)
			page = 1;
		toSend.put("page", page).put("count", count);
		setResMsg(toSend.finishByJson());
	}
}
