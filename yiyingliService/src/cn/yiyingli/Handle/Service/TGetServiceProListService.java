package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class TGetServiceProListService extends TMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		int page = getData().getInt("page");
		List<ServicePro> servicePros = getServiceProService().queryListByTeacherId(teacher.getId(), page,
				ServiceProService.TEACHER_PAGE_SIZE);
		ExList sends = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleDetailServiceForTeacher(servicePro, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", teacher.getServiceProNumberForTeacher());
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}

}
