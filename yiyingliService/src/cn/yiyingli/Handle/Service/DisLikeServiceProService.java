package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class DisLikeServiceProService extends UMsgService {

	private ServiceProService serviceProService;

	private TeacherService teacherService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		User user = getUser();
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"), true);
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22008"));
			return;
		}
		if (getServiceProService().updateUserUnlike(servicePro.getId(), user.getId())) {
			getTeacherService().updateAddMile(servicePro.getTeacher().getId(), -1F);
		};

		setResMsg(MsgUtil.getSuccessMsg("disliked successfully"));
	}

}
