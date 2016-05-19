package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.LikeAndFinishNoShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FGetRecommendTeacherListService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") || getData().containsKey("uid");
	}

	@Override
	public void doit() {
		String result = "";
		boolean isUser = false;
		long userId = 0L;
		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				isUser = true;
				userId = user.getId();
			}
		}
		if (isUser) {
			if (getData().containsKey("num")) {
				result = SendMsgToBaiduUtil.getRecommendListIndividuation(userId + "", getData().getInt("num"));
			} else {
				result = SendMsgToBaiduUtil.getRecommendListIndividuation(userId + "");
			}
		} else {
			String teacherId = (String) getData().get("teacherId");
			result = SendMsgToBaiduUtil.getRecommendListAbout(teacherId);
		}
		getTeacherInfo(result);
	}

	private void getTeacherInfo(String result) {
		JSONObject r = JSONObject.fromObject(result);
		if (r.getInt("Code") != 300 && r.getInt("Code") != 301 && r.getInt("Code") != 302 && r.getInt("Code") != 303
				&& r.getInt("Code") != 304) {
			setResMsg(MsgUtil.getErrorMsgByCode("53001"));
			return;
		}
		JSONArray ra = r.getJSONArray("Results");
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < ra.size(); i++) {
			JSONObject jobj = ra.getJSONObject(i);
			String tid = jobj.getString("ItemId");
			ids.add(Long.valueOf(tid));
		}
		List<Teacher> teachers = null;
		if (ids.size() > 0) {
			teachers = getTeacherService().queryByIds(ids);
		} else {
			teachers = getTeacherService().queryListOnservice(0, 5, false);
		}
		SuperMap map = MsgUtil.getSuccessMap();
		JSONArray jsonTeachers = new JSONArray();
		for (Teacher teacher : teachers) {
			if (teacher.getId().longValue() == getData().getLong("teacherId")) {
				continue;
			}
			JSONObject jsonTeacher = new JSONObject();
			jsonTeacher.put("teacherId", teacher.getId() + "");
			jsonTeacher.put("name", teacher.getName());
			jsonTeacher.put("url", teacher.getIconUrl());
			jsonTeacher.put("topic", teacher.getTopic());
			jsonTeacher.put("simpleInfo", teacher.getSimpleInfo());
			LikeAndFinishNoShowUtil.setFinishNo(teacher, jsonTeacher);
			LikeAndFinishNoShowUtil.setLikeNo(teacher, jsonTeacher);
			jsonTeachers.add(jsonTeacher);
		}
		map.put("data", jsonTeachers.toString());
		setResMsg(map.finishByJson());
	}

}
