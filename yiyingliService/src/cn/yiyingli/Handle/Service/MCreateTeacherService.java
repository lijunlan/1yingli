package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTServiceUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MCreateTeacherService extends MMsgService {

	private TeacherService teacherService;

	private UserService userService;

	private TipService tipService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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
			setResMsg(MsgUtil.getErrorMsgByCode("32002"));
			return;
		}

		JSONObject tdata = getData().getJSONObject("teacher");
		JSONArray workExperiences = tdata.getJSONArray("workExperience");
		JSONArray studyExperiences = tdata.getJSONArray("studyExperience");
		JSONObject service = tdata.getJSONObject("service");
		JSONArray tips = service.getJSONArray("tips");

		String simpleinfo = tdata.getString("simpleinfo");
		String name = tdata.getString("name");
		String phone = tdata.getString("phone");
		String address = tdata.getString("address");
		String mail = tdata.getString("email");
		String iconUrl = tdata.getString("iconUrl");
		String introduce = tdata.getString("introduce");
		String checkPhone = tdata.getString("checkPhone");
		String checkIDCard = tdata.getString("checkIDCard");
		String checkEmail = tdata.getString("checkEmail");
		String checkWork = tdata.getString("checkWork");
		String checkStudy = tdata.getString("checkStudy");
		String showWeight1 = tdata.getString("showWeight1");
		String showWeight2 = tdata.getString("showWeight2");
		String showWeight4 = tdata.getString("showWeight4");
		String showWeight8 = tdata.getString("showWeight8");
		String showWeight16 = tdata.getString("showWeight16");
		String homeWeight = tdata.getString("homeWeight");
		String saleWeight = tdata.getString("saleWeight");
		Teacher teacher = PTeacherUtil.assembleTeacherByManager(user, workExperiences, studyExperiences, tips,
				simpleinfo, name, phone, address, mail, iconUrl, introduce, checkPhone, checkIDCard, checkEmail,
				checkWork, checkStudy, showWeight1, showWeight2, showWeight4, showWeight8, showWeight16, homeWeight,
				saleWeight, getTipService());

		String serviceTitle = service.getString("title");
		String serviceTime = service.getString("time");
		String servicePrice = service.getString("price");
		String serviceTimePerWeek = service.getString("timeperweek");
		String serviceContent = service.getString("content");

		ServicePro servicePro = new ServicePro();
		PTServiceUtil.assembleWithTeacherByManager(teacher, serviceTitle, serviceTime, servicePrice, serviceTimePerWeek,
				serviceContent, tService);
		getTeacherService().saveWithDetailInfo(teacher);
		setResMsg(MsgUtil.getSuccessMsg("insert teacher successfully"));
	}

}
