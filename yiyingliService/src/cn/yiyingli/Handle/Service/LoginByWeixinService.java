package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.TimeTaskUtil;
import cn.yiyingli.Weixin.Util.GetSingleUserWeixinInfoUtil;
import net.sf.json.JSONObject;

public class LoginByWeixinService extends MsgService {

	private UserService userService;

	private UserMarkService userMarkService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		// getData().containsKey("kind")
		return getData().containsKey("weixin_code");
	}

	private void returnUser(User user) {
		String _UUID = UUID.randomUUID().toString();
		getUserMarkService().save(String.valueOf(user.getId()), _UUID);
		user.setLastLoginTime("" + Calendar.getInstance().getTimeInMillis());
		getUserService().update(user);
		if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
			setResMsg("{\"uid\":\"" + _UUID + "\",\"nickName\":\"" + user.getNickName() + "\",\"iconUrl\":\""
					+ (user.getIconUrl() != null ? user.getIconUrl() : "") + "\",\"state\":\"success\",\"teacherId\":\""
					+ user.getTeacher().getId() + "\"}");
		} else {
			setResMsg("{\"uid\":\"" + _UUID + "\",\"nickName\":\"" + user.getNickName() + "\",\"iconUrl\":\""
					+ (user.getIconUrl() != null ? user.getIconUrl() : "") + "\",\"state\":\"success\"}");
		}
		TimeTaskUtil.sendTimeTask("remove", "userMark",
				(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 12) + "", _UUID);
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
			User user = new User();
			user.setLikeTeacherNumber(0L);
			user.setOrderNumber(0L);
			user.setUsername(weixinNo);
			user.setNickName(nickName);
			user.setReceiveCommentNumber(0L);
			user.setSendCommentNumber(0L);
			user.setIconUrl(icon);
			user.setAddress(address);
			user.setPassword(password);
			user.setCreateTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
			user.setTeacherState(UserService.TEACHER_STATE_OFF_SHORT);
			user.setForbid(false);
			user.setWechatNo(weixinNo);
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
