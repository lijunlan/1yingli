package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MDoneApplicationFormService extends MMsgService {

	private ApplicationFormService applicationFormService;

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("afId") && getData().containsKey("accept")
				&& getData().containsKey("description");
	}

	@Override
	public void doit() {
		Manager manager = getManager();
		String afId = (String) getData().get("afId");
		String accept = (String) getData().get("accept");
		String description = (String) getData().get("description");
		ApplicationForm af = getApplicationFormService().query(Long.valueOf(afId));
		if (af == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32003"));
			return;
		}
		if (af.getState() != ApplicationFormService.APPLICATION_STATE_CHECKING_SHORT) {
			setResMsg(MsgUtil.getErrorMsgByCode("32004"));
			return;
		}

		boolean acpt = Boolean.valueOf(accept);
		User user = af.getUser();
		Teacher teacher = af.getTeacher();
		if (acpt) {
			user.setTeacherState(UserService.TEACHER_STATE_ON_SHORT);
			teacher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			teacher.setOnService(false);
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
