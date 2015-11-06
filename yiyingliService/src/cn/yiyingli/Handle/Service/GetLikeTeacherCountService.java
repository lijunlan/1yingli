package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class GetLikeTeacherCountService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = user.getLikeTeacherNumber();
		if (count % TeacherService.PAGE_SIZE_INT > 0)
			page = (int) (count / TeacherService.PAGE_SIZE_INT) + 1;
		else
			page = (int) (count / TeacherService.PAGE_SIZE_INT);
		if (page == 0)
			page = 1;
		toSend.put("page", page);
		toSend.put("count", count);
		setResMsg(toSend.finishByJson());
	}

}
