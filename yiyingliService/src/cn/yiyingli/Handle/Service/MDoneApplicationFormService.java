package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MDoneApplicationFormService extends MsgService {

	private ApplicationFormService applicationFormService;

	private ManagerMarkService managerMarkService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("afId") && getData().containsKey("accept")
				&& getData().containsKey("description");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		String afId = (String) getData().get("afId");
		String accept = (String) getData().get("accept");
		String description = (String) getData().get("description");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		ApplicationForm af = getApplicationFormService().query(Long.valueOf(afId));
		if (af == null) {
			setResMsg(MsgUtil.getErrorMsg("application is not existed"));
			return;
		}
		if (af.getState() != ApplicationFormService.APPLICATION_STATE_CHECKING_SHORT) {
			setResMsg(MsgUtil.getErrorMsg("application has been checked"));
		}
		boolean acpt = Boolean.valueOf(accept);
		User user = af.getUser();
		Teacher teacher = af.getTeacher();
		if (acpt) {
			user.setTeacherState(UserService.TEACHER_STATE_ON_SHORT);
			teacher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			teacher.setOnService(true);
			af.setState(ApplicationFormService.APPLICATION_STATE_SUCCESS_SHORT);
		} else {
			user.setTeacherState(UserService.TEACHER_STATE_OFF_SHORT);
			af.setState(ApplicationFormService.APPLICATION_STATE_FAILED_SHORT);
		}
		af.setCheckInfo(description);
		af.setEndManager(manager);
		af.setEndTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		getApplicationFormService().update(af);
		setResMsg(MsgUtil.getSuccessMsg("application has been done"));
	}

}
