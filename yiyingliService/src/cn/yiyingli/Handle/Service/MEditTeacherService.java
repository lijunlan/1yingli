package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}

		JSONObject tdata = getData().getJSONObject("teacher");
		JSONArray workExperiences = tdata.getJSONArray("workExperience");
		JSONArray studyExperiences = tdata.getJSONArray("studyExperience");
		// JSONObject service = tdata.getJSONObject("service");
		JSONArray tips = tdata.getJSONArray("tips");

		String topic = tdata.getString("topic");
		float price = Float.valueOf(tdata.getString("price"));

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
		String mile = tdata.getString("mile");
		String onService = tdata.getString("onService");
		String bgUrl = tdata.getString("bgUrl");

		Teacher teacher = user.getTeacher();
		PTeacherUtil.editTeacherByManagerDetail(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone,
				address, mail, iconUrl, introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy,
				showWeight1, showWeight2, showWeight4, showWeight8, showWeight16, homeWeight, saleWeight,
				onService == null ? String.valueOf(teacher.getOnService()) : onService,
				mile == null ? teacher.getMile() : Long.valueOf(mile), topic, price,
				(bgUrl == null || bgUrl.equals("")) ? teacher.getBgUrl() : bgUrl, teacher, getTipService());

		// ServicePro servicePro = teacher.getServicePros().get(0);
		// String serviceTitle = (String) service.get("title");
		// float price = Float.valueOf(service.getString("price"));
		// float priceTemp = Float.valueOf(service.getString("priceTemp"));
		// float numeral = Float.valueOf(service.getString("numeral"));
		// String quantifier = service.getString("quantifier");
		// int kind = service.getInt("kind");
		// String freeTime = service.getString("freeTime");
		// String tip = service.getString("tip");
		// String onshow = service.getString("onshow");
		// String onsale = service.getString("onsale");
		// int count = service.getInt("count");
		// String serviceContent = (String) service.get("content");
		//
		// PServiceProUtil.editWithTeacherByManager(ServiceProService.STYLE_TALK,
		// count, price, priceTemp, numeral, kind,
		// freeTime, tip, onshow, onsale, quantifier, serviceTitle,
		// serviceContent, servicePro);

		getTeacherService().updateWithDetailInfo(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher successfully"));
	}

}
