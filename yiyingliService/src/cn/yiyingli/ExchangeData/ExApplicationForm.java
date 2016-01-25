package cn.yiyingli.ExchangeData;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.ApplicationFormService;
import net.sf.json.JSONObject;

public class ExApplicationForm implements ExDataToShow<ApplicationForm> {

	private String userId;

	private String username;

	private String createTime;

	private String state;

	private String endTime;

	private String phone;

	private String name;

	private String email;

	private ExList workExperience;

	private ExList studyExperience;

	private ExList tips;

	private String checkInfo;

	private String afId;

	private String address;

	private String serviceReason;

	private String serviceTitle;

	private String servicePrice;

	private String serviceTime;

	private String serviceContent;

	private String serviceAdvantage;

	private Boolean online;

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ExList getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(ExList workExperience) {
		this.workExperience = workExperience;
	}

	public ExList getStudyExperience() {
		return studyExperience;
	}

	public void setStudyExperience(ExList studyExperience) {
		this.studyExperience = studyExperience;
	}

	public ExList getTips() {
		return tips;
	}

	public void setTips(ExList tips) {
		this.tips = tips;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getAfId() {
		return afId;
	}

	public void setAfId(String afId) {
		this.afId = afId;
	}

	public String getServiceReason() {
		return serviceReason;
	}

	public void setServiceReason(String serviceReason) {
		this.serviceReason = serviceReason;
	}

	public String getServiceTitle() {
		return serviceTitle;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getServiceAdvantage() {
		return serviceAdvantage;
	}

	public void setServiceAdvantage(String serviceAdvantage) {
		this.serviceAdvantage = serviceAdvantage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private static String state2String(String state) {
		short s = Short.parseShort(state);
		switch (s) {
		case ApplicationFormService.APPLICATION_STATE_CHECKING_SHORT:
			return "审核处理中";
		case ApplicationFormService.APPLICATION_STATE_FAILED_SHORT:
			return "审核未通过";
		case ApplicationFormService.APPLICATION_STATE_SUCCESS_SHORT:
			return "审核通过";
		default:
			return "error";
		}
	}

	@Override
	public String toJson() {
		SuperMap map = new SuperMap();
		map.put("userId", userId);
		map.put("phone", phone);
		map.put("name", name);
		map.put("address", address);
		map.put("email", email);
		map.put("checkInfo", checkInfo);
		map.put("serviceReason", serviceReason);
		map.put("serviceTitle", serviceTitle);
		map.put("serviceAdvantage", serviceAdvantage);
		map.put("serviceContent", serviceContent);
		map.put("servicePrice", servicePrice);
		map.put("serviceTime", serviceTime);
		if ("".equals(endTime) || endTime == null) {
			map.put("endTime", endTime);
		} else {
			map.put("endTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(endTime))));
		}
		map.put("createTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(createTime))));
		map.put("state", state2String(state));
		map.put("workExperience", workExperience);
		map.put("schoolExperience", studyExperience);
		map.put("tips", tips);
		map.put("afId", afId);
		map.put("username", username);
		return map.finishByJson();
	}

	@Override
	public JSONObject finish() {
		SuperMap map = new SuperMap();
		map.put("userId", userId);
		map.put("phone", phone);
		map.put("name", name);
		map.put("address", address);
		map.put("email", email);
		map.put("checkInfo", checkInfo);
		map.put("serviceReason", serviceReason);
		map.put("serviceTitle", serviceTitle);
		map.put("serviceAdvantage", serviceAdvantage);
		map.put("serviceContent", serviceContent);
		map.put("servicePrice", servicePrice);
		map.put("serviceTime", serviceTime);
		map.put("serviceOnline", online);
		if ("".equals(endTime) || endTime == null) {
			map.put("endTime", endTime);
		} else {
			map.put("endTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(endTime))));
		}
		map.put("createTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(createTime))));
		map.put("state", state2String(state));
		map.put("workExperience", workExperience);
		map.put("schoolExperience", studyExperience);
		map.put("tips", tips);
		map.put("afId", afId);
		map.put("username", username);
		return map.finish();
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void setUpByPersistant(ApplicationForm persistant) {
		setCreateTime(persistant.getCreateTime());
		setEndTime(persistant.getEndTime());
		setCheckInfo(persistant.getCheckInfo());
		setState(String.valueOf(persistant.getState()));
		User user = persistant.getUser();
		setUsername(user.getUsername());
		Teacher teacher = persistant.getTeacher();
		setEmail(teacher.getEmail());
		setName(teacher.getName());
		setPhone(teacher.getPhone());
		setAddress(teacher.getAddress());
		setUserId(String.valueOf(persistant.getUser().getId()));
		setAfId(String.valueOf(persistant.getId()));
		TService tService = teacher.gettService();
		setServiceAdvantage(tService.getAdvantage());
		setServiceContent(tService.getContent());
		setOnline(tService.getOnline());
		setServiceReason(tService.getReason());
		setServicePrice(String.valueOf(tService.getPriceTotal()));
		setServiceTime(String.valueOf(tService.getTime()));
		setServiceTitle(tService.getTitle());
		workExperience = new ExArrayList();
		studyExperience = new ExArrayList();
		tips = new ExArrayList();
		for (WorkExperience we : teacher.getWorkExperiences()) {
			SuperMap map = new SuperMap();
			map.put("company", we.getCompanyName());
			map.put("endTime", we.getEndTime());
			map.put("weId", we.getId());
			map.put("startTime", we.getStartTime());
			map.put("description", we.getDescription());
			map.put("position", we.getPosition());
			workExperience.add(map.finish());
		}
		for (StudyExperience se : teacher.getStudyExperiences()) {
			SuperMap map = new SuperMap();
			map.put("endTime", se.getEndTime());
			map.put("school", se.getSchoolName());
			map.put("seId", se.getId());
			map.put("startTime", se.getStartTime());
			map.put("major", se.getMajor());
			map.put("degree", se.getDegree());
			map.put("description", se.getDescription());
			studyExperience.add(map.finish());
		}
		for (Tip t : teacher.getTips()) {
			SuperMap map = new SuperMap();
			map.put("tId", t.getId());
			map.put("name", t.getName());
			tips.add(map.finish());
		}
	}

}
