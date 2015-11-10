package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderCountService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		int page = 0;
		long count = user.getOrderNumber();
		if (count % OrderService.PAGE_SIZE_INT > 0)
			page = (int) (count / OrderService.PAGE_SIZE_INT) + 1;
		else
			page = (int) (count / OrderService.PAGE_SIZE_INT);
		if (page == 0)
			page = 1;
		setResMsg(MsgUtil.getSuccessMap().put("count", count).put("page", page).finishByJson());
	}

}
