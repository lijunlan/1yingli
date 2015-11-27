package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTServiceUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;

public class MEditTeacherService extends MMsgService {

	private TeacherService teacherService;

	private UserService userService;

	private TipService tipService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("teacher") && getData().containsKey("username");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doit() {
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}

		Map<String, Object> tdata = (Map<String, Object>) getData().get("teacher");
		List<Object> workExperiences = (List<Object>) tdata.get("workExperience");
		List<Object> studyExperiences = (List<Object>) tdata.get("studyExperience");
		Map<String, Object> service = (Map<String, Object>) tdata.get("service");
		List<Object> tips = (List<Object>) service.get("tips");

		String simpleinfo = (String) tdata.get("simpleinfo");
		String name = (String) tdata.get("name");
		String phone = (String) tdata.get("phone");
		String address = (String) tdata.get("address");
		String mail = (String) tdata.get("email");
		String iconUrl = (String) tdata.get("iconUrl");
		String introduce = (String) tdata.get("introduce");
		String checkPhone = (String) tdata.get("checkPhone");
		String checkIDCard = (String) tdata.get("checkIDCard");
		String checkEmail = (String) tdata.get("checkEmail");
		String checkWork = (String) tdata.get("checkWork");
		String checkStudy = (String) tdata.get("checkStudy");
		String showWeight1 = (String) tdata.get("showWeight1");
		String showWeight2 = (String) tdata.get("showWeight2");
		String showWeight4 = (String) tdata.get("showWeight4");
		String showWeight8 = (String) tdata.get("showWeight8");
		String showWeight16 = (String) tdata.get("showWeight16");
		String homeWeight = (String) tdata.get("homeWeight");
		String saleWeight = (String) tdata.get("saleWeight");
		String mile = (String) tdata.get("mile");

		Teacher teacher = user.getTeacher();
		PTeacherUtil.refreshTeacher(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone, address,
				mail, iconUrl, introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy, showWeight1,
				showWeight2, showWeight4, showWeight8, showWeight16, homeWeight, saleWeight,
				mile == null ? teacher.getMile() : Long.valueOf(mile), teacher, getTipService());

		TService tService = teacher.gettService();
		String serviceTitle = (String) service.get("title");
		String serviceTime = (String) service.get("time");
		String servicePrice = (String) service.get("price");
		String serviceTimePerWeek = (String) service.get("timeperweek");
		String serviceContent = (String) service.get("content");

		if (service.containsKey("onsale") && service.containsKey("pricetemp")) {
			PTServiceUtil.editWithTeacherByManager(teacher, tService, serviceTitle, serviceTime, servicePrice,
					serviceTimePerWeek, serviceContent, (String) service.get("onsale"),
					(String) service.get("pricetemp"));
		} else {
			PTServiceUtil.editWithTeacherByManager(teacher, tService, serviceTitle, serviceTime, servicePrice,
					serviceTimePerWeek, serviceContent, tService.getOnSale() + "", tService.getPriceTemp() + "");
		}
		getTeacherService().updateWithDetailInfo(teacher,true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher successfully"));
	}

}
