package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.ULoginMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Weixin.Util.GetSingleUserWeixinInfoUtil;
import cn.yiyingli.toPersistant.PUserUtil;
import net.sf.json.JSONObject;

public class LoginByWeixinService extends ULoginMsgService {

	@Override
	protected boolean checkData() {
		// getData().containsKey("kind")
		return getData().containsKey("weixin_code");
	}

	@Override
	public void doit() {
		String code = (String) getData().get("weixin_code");
		JSONObject accessToken = null;
		if (getData().containsKey("kind")) {
			if ("mobile".equals(getData().get("kind"))) {
				accessToken = GetSingleUserWeixinInfoUtil.getAccessToken(code, GetSingleUserWeixinInfoUtil.KIND_MOBILE);
			} else {
				accessToken = GetSingleUserWeixinInfoUtil.getAccessToken(code, GetSingleUserWeixinInfoUtil.KIND_WEB);
			}
		} else {
			accessToken = GetSingleUserWeixinInfoUtil.getAccessToken(code, GetSingleUserWeixinInfoUtil.KIND_WEB);
		}
		if (accessToken == null || accessToken.containsKey("errcode")) {
			setResMsg(MsgUtil.getErrorMsg(
					(String) (accessToken == null ? "your weixin code is wrong" : accessToken.get("errmsg"))));
			return;
		}
		JSONObject userInfo = GetSingleUserWeixinInfoUtil.getUserInfo((String) accessToken.get("openid"),
				(String) accessToken.get("access_token"));
		if (userInfo == null || userInfo.containsKey("errcode")) {
			setResMsg(MsgUtil.getErrorMsg(
					(String) (accessToken == null ? "your weixin code is wrong" : accessToken.get("errmsg"))));
			return;
		}
		String p = (String) userInfo.get("province");
		String ci = (String) userInfo.get("city");
		String co = (String) userInfo.get("country");

		String weixinNo = "wx" + userInfo.get("unionid");
		String password = (String) userInfo.get("unionid");
		String nickName = (String) userInfo.get("nickname");
		if (nickName.length() > 7) {
			nickName = nickName.substring(0, 7);
		}
		String icon = (String) userInfo.get("headimgurl");
		String address = co + " " + p + " " + ci;

		User u = getUserService().queryWithWeixin(weixinNo, false);
		if (u == null) {
			password = MD5Util.MD5(password);
			User user = PUserUtil.assembleUser(weixinNo, password, nickName, icon, address, null);
			try {
				getUserService().save(user);
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsg(e.getMessage()));
				return;
			}
			returnUser(user);
		} else {
			returnUser(u);
		}

	}

}
