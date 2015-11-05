package cn.yiyingli.Handle.Service;

import org.springframework.web.util.HtmlUtils;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class ChangeIconService extends UMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("iconUrl");
	}

	@Override
	public void doit() {
		super.doit();
		User user = getUser();
		String iconUrl = (String) getData().get("iconUrl");
		iconUrl = HtmlUtils.htmlEscape(iconUrl);
		user.setIconUrl(iconUrl);
		getUserService().updateWithTeacher(user);
		setResMsg(MsgUtil.getSuccessMap().put("iconUrl", iconUrl).finishByJson());
	}

}
