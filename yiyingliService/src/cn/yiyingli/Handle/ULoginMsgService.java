package cn.yiyingli.Handle;

import java.util.Calendar;
import java.util.UUID;

import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.TimeTaskUtil;

public abstract class ULoginMsgService extends UMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	protected void returnUser(User user) {
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
}
