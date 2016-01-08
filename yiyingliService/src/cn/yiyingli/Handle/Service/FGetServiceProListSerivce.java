package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class FGetServiceProListSerivce extends MsgService {

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
		return getData().containsKey("teacherId") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		long teacherId = getData().getLong("teacherId");
		long tid = Long.valueOf((String) getData().get("teacherId"));
		Teacher teacher = getTeacherService().query(tid);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		int page = Integer.valueOf((String) getData().get("page"));
		List<ServicePro> servicePros = getServiceProService().queryListByTeacherIdAndShowAndState(teacherId, true,
				ServiceProService.STATE_OK, page);

		ExList sends = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleSimpleServiceForUser(servicePro, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", teacher.getServiceProNumberForUser());
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}

}
