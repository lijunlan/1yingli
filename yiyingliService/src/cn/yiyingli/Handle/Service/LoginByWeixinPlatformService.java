package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.ULoginMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Weixin.Util.GetSingleUserWeixinInfoPlatformUtil;
import cn.yiyingli.Weixin.Util.GetSingleUserWeixinInfoUtil;
import cn.yiyingli.toPersistant.PUserUtil;
import net.sf.json.JSONObject;

public class LoginByWeixinPlatformService extends ULoginMsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("weixin_code");
	}

	@Override
	public void doit() {
		String code = (String) getData().get("weixin_code");
		JSONObject accessToken = null;
		accessToken = GetSingleUserWeixinInfoPlatformUtil.getAccessToken(code);

		if (accessToken == null || accessToken.containsKey("errcode")) {
			LogUtil.error((String) (accessToken == null ? "your weixin code is wrong" : accessToken.get("errmsg")),
					this.getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("12019"));
			return;
		}
		JSONObject userInfo = GetSingleUserWeixinInfoUtil.getUserInfo((String) accessToken.get("openid"),
				(String) accessToken.get("access_token"));
		if (userInfo == null || userInfo.containsKey("errcode")) {
			LogUtil.error((String) (accessToken == null ? "your weixin code is wrong" : accessToken.get("errmsg")),
					this.getClass());
			setResMsg(MsgUtil.getErrorMsgByCode("12019"));
			return;
		}
		String p = (String) userInfo.get("province");
		String ci = (String) userInfo.get("city");
		String co = (String) userInfo.get("country");

		String weixinNoU = "wx" + userInfo.getString("unionid");
		String weixinNo = "wx" + userInfo.get("openid");
		String password = (String) userInfo.get("openid");
		String nickName = (String) userInfo.get("nickname");
		if (nickName.length() > 7) {
			nickName = nickName.substring(0, 7);
		}
		String icon = (String) userInfo.get("headimgurl");
		String address = co + " " + p + " " + ci;

		User u = getUserService().queryWithWeixin(weixinNoU, false);
		if (u == null) {
			u = getUserService().queryWithWeixinPlatform(weixinNo);
			if (u != null && u.getWechatNo() == null) {
				u.setWechatNo(weixinNoU);
			}
		} else {
			if (u.getWechatPlatformNo() == null) {
				u.setWechatPlatformNo(weixinNo);
			}
		}
		if (u == null) {
			password = MD5Util.MD5(password);
			User user = PUserUtil.assembleUserFromWXPlatform(weixinNoU, weixinNo, password, nickName, icon, address);
			try {
				getUserService().save(user);
			} catch (Exception e) {
				e.printStackTrace();
				setResMsg(MsgUtil.getErrorMsgByCode("15003"));
				return;
			}
			returnUser(user);
		} else {
			if (u.getWechatPlatformNo() == null) {
				u.setWechatPlatformNo(weixinNo);
			}
//			if (!u.getIconUrl().equals(icon)) {
//				u.setIconUrl(icon);
//			}
			returnUser(u);
		}

	}
}
