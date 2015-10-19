package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class GetUserSimpleInfoService extends MsgService {

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
		setResMsg(MsgUtil.getSuccessMap().put("nickName", user.getNickName()).put("iconUrl", user.getIconUrl())
				.put("isTeacher", user.getTeacherState() == TeacherService.CHECK_STATE_NONE_SHORT ? "no" : "yes")
				.finishByJson());
	}

}
