package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import net.sf.json.JSONObject;

public class TCreateServiceProService extends TMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("servicePro");
	}

	@Override
	public void doit() {
		JSONObject jsonServicePro = getData().getJSONObject("servicePro");
		ServicePro servicePro = new ServicePro();
		Teacher teacher = getTeacher();
		PServiceProUtil.assembleWithTeacherByTeacherCreate(teacher, jsonServicePro, servicePro);
		getServiceProService().saveAndPlusNumber(servicePro, false);
		NotifyUtil.notifyManager(new SuperMap().put("type", "checkServicePro").finishByJson());
		setResMsg(MsgUtil.getSuccessMsg("create servicePro successfully"));
	}

}
