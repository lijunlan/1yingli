package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TSMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class TRemoveServiceProService extends TSMsgService {

	@Override
	public void doit() {
		ServicePro servicePro = getServicePro();
		servicePro.setRemove(true);
		Teacher teacher = servicePro.getTeacher();
		teacher.setServiceProNumberForTeacher(teacher.getServiceProNumberForTeacher() - 1);
		if (servicePro.getOnShow() && servicePro.getState() == ServiceProService.STATE_OK) {
			teacher.setServiceProNumberForUser(teacher.getServiceProNumberForUser() - 1);
		}
		getServiceProService().update(servicePro);
		setResMsg(MsgUtil.getSuccessMsg("remove servicePro successfully"));
	}

}
