package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.ULoginMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.LogUtil;
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

		String weixinNo = "wx" + userInfo.get("unionid");
		String password = (String) userInfo.get("unionid");
		String nickName = (String) userInfo.get("nickname");
		if (nickName.length() > 7) {
			nickName = nickName.substring(0, 7);
		}
		String icon = (String) userInfo.get("headimgurl");
		String address = co + " " + p + " " + ci;

		boolean isLogin = true;
		if (getData().containsKey("isLogin") && getData().getInt("isLogin") == 0) {
			isLogin = false;
		}

		User u = getUserService().queryWithWeixin(weixinNo, false);
		if (u == null) {
			password = MD5Util.MD5(password);
			String nowIcon = updateIcon(icon,weixinNo);
			User user = PUserUtil.assembleUserFromWX(weixinNo, password, nickName, nowIcon, address);
			try {
				getUserService().save(user);
			} catch (Exception e) {
				e.printStackTrace();
				setResMsg(MsgUtil.getErrorMsgByCode("15003"));
				return;
			}
			returnUser(user,true, isLogin);
		} else {
			if(!u.getIconUrl().startsWith("http://image.1yingli.cn")){
				String nowIcon = updateIcon(u.getIconUrl(),weixinNo);
				u.setIconUrl(nowIcon);
			}
			returnUser(u,false, isLogin);
		}

	}

}
