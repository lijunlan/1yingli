package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.ULoginMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.WeiboUtil.GetSingleUserWeiboInfoUtil;
import cn.yiyingli.toPersistant.PUserUtil;
import weibo4j.http.AccessToken;

public class LoginByWeiboService extends ULoginMsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("weibo_code")
				|| (getData().containsKey("weibo_accessToken") && getData().containsKey("weibo_uid"));
	}

	@Override
	public void doit() {
		weibo4j.model.User weiboUser = null;
		if (getData().containsKey("weibo_code")) {
			String code = (String) getData().get("weibo_code");
			AccessToken accessToken = GetSingleUserWeiboInfoUtil.getAccessToken(code);
			if (accessToken == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("12018"));
				return;
			}
			weiboUser = GetSingleUserWeiboInfoUtil.getUserInfo(accessToken);
		} else {
			String accessToken = (String) getData().get("weibo_accessToken");
			String uid = (String) getData().get("weibo_uid");
			weiboUser = GetSingleUserWeiboInfoUtil.getUserInfo(accessToken, uid);
		}

		if (weiboUser == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("12018"));
			return;
		}

		String weiboNo = "wb" + weiboUser.getId();
		String password = weiboUser.getId();
		String nickName = weiboUser.getScreenName();
		if (nickName.length() > 7) {
			nickName = nickName.substring(0, 7);
		}
		String icon = weiboUser.getAvatarLarge();
		String address = weiboUser.getLocation();

		User u = getUserService().queryWithWeibo(weiboNo, false);
		if (u == null) {
			password = MD5Util.MD5(password);
			User user = PUserUtil.assembleUserFromWB(weiboNo, password, nickName, icon, address);
			try {
				getUserService().save(user);
			} catch (Exception e) {
				e.printStackTrace();
				setResMsg(MsgUtil.getErrorMsgByCode("15003"));
				return;
			}
			returnUser(user);
		} else {
			if (!u.getIconUrl().equals(icon)) {
				u.setIconUrl(icon);
			}
			returnUser(u);
		}

	}

}