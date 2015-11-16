package cn.yiyingli.Handle;

import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public abstract class TMsgService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private Teacher teacher;

	private User user;

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

	protected Teacher getTeacher() {
		return teacher;
	}

	private void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	protected User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("teacherId");
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
		Teacher teacher = getTeacherService().queryByUserId(user.getId(), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("24001"));
			return false;
		}
		setTeacher(teacher);
		String teacherId = (String) getData().get("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsgByCode("24002"));
			return false;
		}
		return true;
	}
}
