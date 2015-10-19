package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderCountService extends MsgService {

	private UserMarkService userMarkService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
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
