package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TSMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import net.sf.json.JSONObject;

public class TEditServiceProService extends TSMsgService {

	public boolean checkData() {
		return super.checkData() && getData().containsKey("servicePro");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServicePro();
		JSONObject jsonServicePro = getData().getJSONObject("servicePro");
		PServiceProUtil.assembleByTeacherEdit(jsonServicePro, servicePro);
		if (!servicePro.getOnShow()) {
			Teacher teacher = servicePro.getTeacher();
			teacher.setServiceProNumberForUser(teacher.getServiceProNumberForUser() - 1);
		}
		getServiceProService().update(servicePro);
		setResMsg(MsgUtil.getSuccessMsg("edit servicePro successfully"));
	}

}
