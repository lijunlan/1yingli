package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MGetServiceProListService extends MMsgService {

	private ServiceProService serviceProService;

	private UserService userService;

	private TeacherService teacherService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("page") || getData().containsKey("state")
				|| getData().containsKey("username");
	}

	@Override
	public void doit() {
		int page = getData().getInt("page");
		List<ServicePro> servicePros = null;
		if (getData().containsKey("username")) {
			String username = (String) getData().get("username");
			User user = getUserService().queryWithTeacher(username, false);
			Long teacherId;
			if (user != null) {
				teacherId = user.getTeacher().getId();
			} else {
				Teacher teacher = getTeacherService().queryByName(username);
				if (teacher == null) {
					try {
						teacher = getTeacherService().query(Long.parseLong(username));
					} catch (NumberFormatException e) {
						setResMsg(MsgUtil.getErrorMsgByCode("32015"));
						return;
					}
				}
				teacherId = teacher.getId();
			}
			servicePros = getServiceProService().queryListByTeacherId(teacherId, page,
					ServiceProService.MANAGER_PAGE_SIZE);
		} else if (getData().containsKey("state")) {
			short state = Short.parseShort(getData().getString("state"));
			servicePros = getServiceProService().queryListByState(state, page);
		} else {
			servicePros = getServiceProService().queryList(page, ServiceProService.MANAGER_PAGE_SIZE);
		}
		ExList toSend = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleSimpleServiceProForManager(servicePro, map);
			toSend.add(map.finish());
		}
		SuperMap send = MsgUtil.getSuccessMap().put("data", toSend);
		setResMsg(send.finishByJson());
	}

}
