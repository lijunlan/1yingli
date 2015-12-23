package cn.yiyingli.Handle;

import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public abstract class TSMsgService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private ServiceProService serviceProService;

	private Teacher teacher;

	private User user;

	private ServicePro servicePro;

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

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	protected ServicePro getServicePro() {
		return servicePro;
	}

	private void setServicePro(ServicePro servicePro) {
		this.servicePro = servicePro;
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
		return getData().containsKey("uid") && getData().containsKey("teacherId")
				&& getData().containsKey("serviceProId");
	}

	@Override
	public boolean validate() {
		String uid = getData().getString("uid");
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
		String teacherId = getData().getString("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsgByCode("24002"));
			return false;
		}
		setTeacher(teacher);
		long serviceId = getData().getLong("serviceProId");
		ServicePro servicePro = getServiceProService().queryByTeacherIdAndServiceId(teacher.getId(), serviceId);
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("24003"));
			return false;
		}
		setServicePro(servicePro);
		return true;
	}

}
