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
import cn.yiyingli.WeiboUtil.GetSingleUserWeiboInfoUtil;
import weibo4j.http.AccessToken;

public class LoginByWeiboService extends MsgService {

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
		return getData().containsKey("weibo_code")
				|| (getData().containsKey("weibo_accessToken") && getData().containsKey("weibo_uid"));
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
		weibo4j.model.User weiboUser = null;
		if (getData().containsKey("weibo_code")) {
			String code = (String) getData().get("weibo_code");
			AccessToken accessToken = GetSingleUserWeiboInfoUtil.getAccessToken(code);
			if (accessToken == null) {
				setResMsg(MsgUtil.getErrorMsg("your weibo code is wrong"));
				return;
			}
			weiboUser = GetSingleUserWeiboInfoUtil.getUserInfo(accessToken);
		} else {
			String accessToken = (String) getData().get("weibo_accessToken");
			String uid = (String) getData().get("weibo_uid");
			weiboUser = GetSingleUserWeiboInfoUtil.getUserInfo(accessToken, uid);
		}

		if (weiboUser == null) {
			setResMsg(MsgUtil.getErrorMsg("your weibo code is wrong"));
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
			User user = new User();
			user.setLikeTeacherNumber(0L);
			user.setOrderNumber(0L);
			user.setUsername(weiboNo);
			user.setNickName(nickName);
			user.setReceiveCommentNumber(0L);
			user.setSendCommentNumber(0L);
			user.setIconUrl(icon);
			user.setAddress(address);
			user.setPassword(password);
			user.setCreateTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
			user.setTeacherState(UserService.TEACHER_STATE_OFF_SHORT);
			user.setForbid(false);
			user.setWeiboNo(weiboNo);
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