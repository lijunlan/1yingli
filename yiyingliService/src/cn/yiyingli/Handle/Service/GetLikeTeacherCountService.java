package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class GetLikeTeacherCountService extends MsgService {

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
