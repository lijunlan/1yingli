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
		List<ServicePro> servicePros;
		int count;
		if (getData().containsKey("state")) {
			short state = (short) getData().getInt("state");
			servicePros = getServiceProService().queryListByTeacherIdAndState(teacher.getId(), state, page,
					ServiceProService.TEACHER_PAGE_SIZE);
			count = getServiceProService().sumNumByTeacherIdAndState(teacher.getId(), state);
		} else {
			servicePros = getServiceProService().queryListByTeacherId(teacher.getId(), page,
					ServiceProService.TEACHER_PAGE_SIZE);
			count = teacher.getServiceProNumberForTeacher();
		}
		short[] states = {0,1,2};
		int[] counts = new int[3];
		for (int i = 0;i<3;i++) {
			counts[i] = getServiceProService().sumNumByTeacherIdAndState(teacher.getId(), states[i]);
		}
		ExList sends = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleDetailServiceForTeacher(servicePro, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", count);
		toSend.put("counts", counts);
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}

}
