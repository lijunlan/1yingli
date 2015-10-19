package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class LikeTeacherService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private UserService userService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		Teacher teacher = getTeacherService().query(Long.valueOf(teacherId), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("teahcerId is not existed"));
			return;
		}
		getTeacherService().updateUserLike(teacher, user);
		setResMsg(MsgUtil.getSuccessMsg("liked"));
	}

}
