package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
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

	@SuppressWarnings("unchecked")
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
		JSONObject service = tdata.getJSONObject("service");
		JSONArray tips = service.getJSONArray("tips");

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
		String onService = (String) tdata.get("onService");

		Teacher teacher = user.getTeacher();
		PTeacherUtil.editTeacherByManagerDetail(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone,
				address, mail, iconUrl, introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy,
				showWeight1, showWeight2, showWeight4, showWeight8, showWeight16, homeWeight, saleWeight,
				onService == null ? String.valueOf(teacher.getOnService()) : onService,
				mile == null ? teacher.getMile() : Long.valueOf(mile), teacher, getTipService());

		ServicePro servicePro = teacher.getServicePros().get(0);
		String serviceTitle = (String) service.get("title");
		float price = Float.valueOf(service.getString("price"));
		float priceTemp = Float.valueOf(service.getString("priceTemp"));
		float numeral = Float.valueOf(service.getString("numeral"));
		String quantifier = service.getString("quantifier");
		int kind = service.getInt("kind");
		String freeTime = service.getString("freeTime");
		String tip = service.getString("tip");
		String onshow = service.getString("onshow");
		String onsale = service.getString("onsale");
		int count = service.getInt("count");
		String serviceContent = (String) service.get("content");

		PServiceProUtil.editWithTeacherByManager(ServiceProService.STYLE_TALK, count, price, priceTemp, numeral, kind,
				freeTime, tip, onshow, onsale, quantifier, serviceTitle, serviceContent, servicePro);

		getTeacherService().updateWithDetailInfo(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher successfully"));
	}

}
