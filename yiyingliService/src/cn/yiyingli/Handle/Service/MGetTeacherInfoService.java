package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MGetTeacherInfoService extends MMsgService {

	private TeacherService teacherService;

	private UserService userService;

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

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("username");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("12015"));
			return;
		}
		try {
			Teacher teacher = getTeacherService().queryAll(user.getTeacher().getId());
			JSONObject t = new JSONObject();
			t.put("name", teacher.getName());
			t.put("phone", teacher.getPhone());
			t.put("address", teacher.getAddress());
			t.put("email", teacher.getEmail());
			t.put("iconUrl", teacher.getIconUrl());
			t.put("introduce", teacher.getIntroduce());
			t.put("simpleinfo", teacher.getSimpleInfo());
			t.put("showWeight1", teacher.getShowWeight1());
			t.put("showWeight2", teacher.getShowWeight2());
			t.put("showWeight4", teacher.getShowWeight4());
			t.put("showWeight8", teacher.getShowWeight8());
			t.put("showWeight16", teacher.getShowWeight16());
			t.put("homeWeight", teacher.getHomeWeight());
			t.put("saleWeight", teacher.getSaleWeight());
			t.put("checkIDCard",
					teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
			t.put("checkStudy",
					teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
			t.put("checkWork",
					teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
			t.put("checkPhone", teacher.getCheckPhone() + "");
			t.put("checkEmail", teacher.getCheckEmail() + "");

			TService tService = teacher.gettService();
			t.put("serviceTitle", tService.getTitle());
			t.put("serviceTime", tService.getTime() + "");
			t.put("servicePrice", tService.getPriceTotal() + "");
			t.put("serviceTimePerWeek", tService.getTimesPerWeek() + "");
			t.put("serviceContent", tService.getContent());

			JSONArray tips = new JSONArray();
			for (Tip tip : teacher.getTips()) {
				JSONObject temp = new JSONObject();
				temp.put("id", tip.getId());
				tips.add(temp);
			}
			t.put("tips", tips);

			JSONArray workExp = new JSONArray();
			for (WorkExperience we : teacher.getWorkExperiences()) {
				JSONObject temp = new JSONObject();
				temp.put("companyName", we.getCompanyName());
				temp.put("position", we.getPosition());
				temp.put("startTime", we.getStartTime());
				temp.put("endTime", we.getEndTime());
				temp.put("description", we.getDescription());
				workExp.add(temp);
			}
			t.put("workExperience", workExp);

			JSONArray studyExp = new JSONArray();
			for (StudyExperience se : teacher.getStudyExperiences()) {
				JSONObject temp = new JSONObject();
				temp.put("schoolName", se.getSchoolName());
				temp.put("major", se.getMajor());
				temp.put("degree", se.getDegree());
				temp.put("description", se.getDescription());
				temp.put("startTime", se.getStartTime());
				temp.put("endTime", se.getEndTime());
				studyExp.add(temp);
			}
			t.put("studyExperience", studyExp);

			JSONObject toSend = new JSONObject();
			toSend.put("teacher", t);
			toSend.put("state", "success");
			setResMsg(toSend.toString());
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("31001"));
		}
	}

}
