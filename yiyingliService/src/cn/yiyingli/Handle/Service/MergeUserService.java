package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MergeUserService extends UMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("subUid");
	}

	@Override
	public void doit() {
		User user = getUser();
		String subUid = (String) getData().get("subUid");
		User subUser = getUserMarkService().queryUser(subUid);
		if (subUser == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		if (subUser.getFaUserId() != null) {
			setResMsg(MsgUtil.getErrorMsgByCode("15007"));
			return;
		}
		if (subUser.getId().equals(user.getId())) {
			setResMsg(MsgUtil.getErrorMsgByCode("15007"));
			return;
		}
		if(subUser.getTeacher() != null && subUser.getTeacher().getOnService()) {
			setResMsg(MsgUtil.getErrorMsgByCode("15005"));
			return;
		}
		getUserService().mergeUser(user,subUser);
		setResMsg(MsgUtil.getSuccessMsg("merge User Success"));
	}
}
