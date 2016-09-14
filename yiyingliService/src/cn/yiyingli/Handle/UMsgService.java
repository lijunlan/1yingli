package cn.yiyingli.Handle;

import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

/**
 * 用户专用抽象类</b>已经实现uid鉴权，并且已经获取user实例
 * 
 * @author sdll18
 *
 */
public abstract class UMsgService extends MsgService {

	private UserMarkService userMarkService;

	private User user;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	protected User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid");
	}

	@Override
	public boolean validate() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return false;
		}
		setUser(user);
		return true;
	}

}
