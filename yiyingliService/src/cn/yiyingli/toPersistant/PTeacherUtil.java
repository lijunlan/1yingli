package cn.yiyingli.toPersistant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PTeacherUtil {

	public static void editTeacherByTeacherSimple(JSONObject map, Teacher teacher) {
		String simpleinfo = map.getString("simpleinfo");
		String address = map.getString("address");
		String topic = map.getString("topic");
		teacher.setSimpleInfo(simpleinfo);
		teacher.setAddress(address);
		teacher.setTopic(topic);
		teacher.setPrice(Float.valueOf(map.getString("price")));
	}

	public static void editTeacherByManagerDetail(User user, JSONArray workExperiences, JSONArray studyExperiences,
			JSONArray tips, String simpleinfo, String name, String phone, String address, String mail, String iconUrl,
			String introduce, String checkPhone, String checkIDCard, String checkEmail, String checkWork,
			String checkStudy, String showWeight1, String showWeight2, String showWeight4, String showWeight8,
			String showWeight16, String homeWeight, String saleWeight, String onService, long mile, String topic,
			float price, Teacher teacher, TipService tipService) {
		refreshTeacher(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone, address, mail, iconUrl,
				introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy, showWeight1, showWeight2,
				showWeight4, showWeight8, showWeight16, homeWeight, saleWeight, mile, topic, price, teacher,
				tipService);
		teacher.setOnService(Boolean.valueOf(onService));
	}

	public static void editTeacherByTeacherDetail(JSONObject map, Teacher teacher, TipService tipService) {
		editTeacherByTeacherSimple(map, teacher);

		String bgUrl = map.getString("bgUrl");
		String introduce = map.getString("introduce");
		teacher.setBgUrl(bgUrl);
		teacher.setIntroduce(introduce);

		JSONArray studyExperiences = map.getJSONArray("studyExperiences");
		JSONArray workExperiences = map.getJSONArray("workExperiences");
		JSONArray tips = map.getJSONArray("tips");

		List<WorkExperience> wes = new ArrayList<WorkExperience>();
		for (Object weobj : workExperiences) {
			JSONObject we = (JSONObject) weobj;
			WorkExperience workExperience = new WorkExperience();
			workExperience.setCompanyName(we.getString("companyName"));
			workExperience.setDescription(we.getString("description"));
			workExperience.setEndTime(we.getString("endTime"));
			workExperience.setStartTime(we.getString("startTime"));
			workExperience.setPosition(we.getString("position"));
			workExperience.setOwnTeacher(teacher);
			wes.add(workExperience);
		}
		teacher.setWorkExperiences(wes);
		List<StudyExperience> ses = new ArrayList<StudyExperience>();
		for (Object seobj : studyExperiences) {
			JSONObject se = (JSONObject) seobj;
			StudyExperience studyExperience = new StudyExperience();
			studyExperience.setDegree(se.getString("degree"));
			studyExperience.setDescription(se.getString("description"));
			studyExperience.setEndTime(se.getString("endTime"));
			studyExperience.setMajor(se.getString("major"));
			studyExperience.setSchoolName(se.getString("schoolName"));
			studyExperience.setStartTime(se.getString("startTime"));
			studyExperience.setOwnTeacher(teacher);
			ses.add(studyExperience);
		}
		teacher.setStudyExperiences(ses);

		teacher.getTips().clear();
		long tipMark = 0;
		if (tips != null) {
			for (Object tobj : tips) {
				JSONObject t = (JSONObject) tobj;
				Long tid = Long.valueOf(t.getString("id"));
				tipMark = tipMark | tid;
				Tip mT = tipService.query(tid);
				teacher.getTips().add(mT);
			}
		}
		teacher.setTipMark(tipMark);
	}

	@SuppressWarnings("unchecked")
	public static void refreshTeacher(User user, JSONArray workExperiences, JSONArray studyExperiences, JSONArray tips,
			String simpleinfo, String name, String phone, String address, String mail, String iconUrl, String introduce,
			String checkPhone, String checkIDCard, String checkEmail, String checkWork, String checkStudy,
			String showWeight1, String showWeight2, String showWeight4, String showWeight8, String showWeight16,
			String homeWeight, String saleWeight, long mile, String topic, float price, Teacher teacher,
			TipService tipService) {
		if (Boolean.valueOf(checkPhone)) {
			teacher.setCheckPhone(true);
		} else {
			teacher.setCheckPhone(false);
		}

		if (Boolean.valueOf(checkIDCard)) {
			teacher.setCheckIDCardState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			teacher.setCheckIDCardState(TeacherService.CHECK_STATE_NONE_SHORT);
		}

		if (Boolean.valueOf(checkEmail)) {
			teacher.setCheckEmail(true);
		} else {
			teacher.setCheckEmail(false);
		}

		if (Boolean.valueOf(checkWork)) {
			teacher.setCheckWorkState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			teacher.setCheckWorkState(TeacherService.CHECK_STATE_NONE_SHORT);
		}

		if (Boolean.valueOf(checkStudy)) {
			teacher.setCheckDegreeState(TeacherService.CHECK_STATE_SUCCESS_SHORT);
		} else {
			teacher.setCheckDegreeState(TeacherService.CHECK_STATE_NONE_SHORT);
		}

		user.setAddress(address);
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(mail);
		user.setIconUrl(iconUrl);
		user.setTeacher(teacher);
		teacher.setUser(user);

		teacher.setTopic(topic);
		teacher.setPrice(price);
		teacher.setMile(mile);
		teacher.setAddress(address);
		teacher.setUsername(user.getUsername());
		teacher.setName(name);
		teacher.setPhone(phone);
		teacher.setEmail(mail);
		teacher.setIconUrl(iconUrl);
		teacher.setIntroduce(introduce);
		teacher.setSimpleInfo(simpleinfo);
		teacher.setShowWeight1(Integer.valueOf(showWeight1.equals("") ? "" : showWeight1));
		teacher.setShowWeight2(Integer.valueOf(showWeight2.equals("") ? "" : showWeight2));
		teacher.setShowWeight4(Integer.valueOf(showWeight4.equals("") ? "" : showWeight4));
		teacher.setShowWeight8(Integer.valueOf(showWeight8.equals("") ? "" : showWeight8));
		teacher.setShowWeight16(Integer.valueOf(showWeight16.equals("") ? "" : showWeight16));
		teacher.setHomeWeight(Integer.valueOf(homeWeight.equals("") ? "" : homeWeight));
		teacher.setSaleWeight(Integer.valueOf(saleWeight.equals("") ? "" : saleWeight));

		List<WorkExperience> wes = new ArrayList<WorkExperience>();
		for (Object we : workExperiences) {
			WorkExperience workExperience = new WorkExperience();
			workExperience.setCompanyName((String) ((Map<String, Object>) we).get("companyName"));
			workExperience.setDescription((String) ((Map<String, Object>) we).get("description"));
			workExperience.setEndTime((String) ((Map<String, Object>) we).get("endTime"));
			workExperience.setStartTime((String) ((Map<String, Object>) we).get("startTime"));
			workExperience.setPosition((String) ((Map<String, Object>) we).get("position"));
			workExperience.setOwnTeacher(teacher);
			wes.add(workExperience);
		}
		teacher.setWorkExperiences(wes);
		List<StudyExperience> ses = new ArrayList<StudyExperience>();
		for (Object se : studyExperiences) {
			StudyExperience studyExperience = new StudyExperience();
			studyExperience.setDegree((String) ((Map<String, Object>) se).get("degree"));
			studyExperience.setDescription((String) ((Map<String, Object>) se).get("description"));
			studyExperience.setEndTime((String) ((Map<String, Object>) se).get("endTime"));
			studyExperience.setMajor((String) ((Map<String, Object>) se).get("major"));
			studyExperience.setSchoolName((String) ((Map<String, Object>) se).get("schoolName"));
			studyExperience.setStartTime((String) ((Map<String, Object>) se).get("startTime"));
			studyExperience.setOwnTeacher(teacher);
			ses.add(studyExperience);
		}

		teacher.getTips().clear();
		teacher.setStudyExperiences(ses);
		long tipMark = 0;
		if (tips != null) {
			for (Object t : tips) {
				Long tid = ((JSONObject) t).getLong("id");
				tipMark = tipMark | tid;
				Tip mT = tipService.query(tid);
				teacher.getTips().add(mT);
			}
		}
		teacher.setTipMark(tipMark);
	}

	public static Teacher assembleNewTeacher(User user, JSONArray workExperiences, JSONArray studyExperiences,
			JSONArray tips, String simpleinfo, String name, String phone, String address, String mail, String iconUrl,
			String introduce, String checkPhone, String checkIDCard, String checkEmail, String checkWork,
			String checkStudy, String showWeight1, String showWeight2, String showWeight4, String showWeight8,
			String showWeight16, String homeWeight, String saleWeight, String topic, float price,
			TipService tipService) {
		Teacher teacher = new Teacher();
		refreshTeacher(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone, address, mail, iconUrl,
				introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy, showWeight1, showWeight2,
				showWeight4, showWeight8, showWeight16, homeWeight, saleWeight, 0L, topic, price, teacher, tipService);
		teacher.setScore(0);
		teacher.setAnswerTime(0L);
		teacher.setAnswerRatio(0F);
		teacher.setPraiseRatio(0F);
		teacher.setCommentNumber(0L);
		teacher.setRewardNumber(0L);
		teacher.setLevel((short) 5);
		teacher.setOrderNumber(0L);
		teacher.setFinishOrderNumber(0L);
		teacher.setLikeNumber(0L);
		teacher.setLookNumber(0L);
		teacher.setPassageNumber(0L);
		teacher.setCheckPassageNumber(0L);
		teacher.setRefusePassageNumber(0L);
		teacher.setServiceProNumberForUser(0);
		teacher.setServiceProNumberForTeacher(0);
		return teacher;
	}

	public static Teacher assembleTeacherByApplication(User user, JSONArray workExperiences, JSONArray studyExperiences,
			JSONArray tips, String simpleinfo, String name, String phone, String address, String mail, String iconUrl,
			String introduce, String checkPhone, String checkIDCard, String checkEmail, String checkWork,
			String checkStudy, String showWeight1, String showWeight2, String showWeight4, String showWeight8,
			String showWeight16, String homeWeight, String saleWeight, String topic, float price,
			TipService tipService) {
		Teacher teacher = assembleNewTeacher(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone,
				address, mail, iconUrl, introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy,
				showWeight1, showWeight2, showWeight4, showWeight8, showWeight16, homeWeight, saleWeight, topic, price,
				tipService);
		user.setTeacherState(UserService.TEACHER_STATE_CHECKING_SHORT);
		teacher.setOnService(false);
		return teacher;
	}

	public static Teacher assembleTeacherByManager(User user, JSONArray workExperiences, JSONArray studyExperiences,
			JSONArray tips, String simpleinfo, String name, String phone, String address, String mail, String iconUrl,
			String introduce, String checkPhone, String checkIDCard, String checkEmail, String checkWork,
			String checkStudy, String showWeight1, String showWeight2, String showWeight4, String showWeight8,
			String showWeight16, String homeWeight, String saleWeight, String topic, float price,
			TipService tipService) {
		Teacher teacher = assembleNewTeacher(user, workExperiences, studyExperiences, tips, simpleinfo, name, phone,
				address, mail, iconUrl, introduce, checkPhone, checkIDCard, checkEmail, checkWork, checkStudy,
				showWeight1, showWeight2, showWeight4, showWeight8, showWeight16, homeWeight, saleWeight, topic, price,
				tipService);
		teacher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		user.setTeacherState(UserService.TEACHER_STATE_ON_SHORT);
		teacher.setOnService(true);
		return teacher;
	}
}
