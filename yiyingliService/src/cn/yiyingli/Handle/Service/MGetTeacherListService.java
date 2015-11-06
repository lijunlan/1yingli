package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetTeacherListService extends MMsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		if (Integer.valueOf(page) <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		List<Teacher> teachers = getTeacherService().queryList(Integer.valueOf(page), false);
		List<String> exTeachers = new ArrayList<String>();
		for (Teacher teacher : teachers) {
			SuperMap map = new SuperMap();
			map.put("address", teacher.getAddress());
			map.put("checkDegree",
					teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "yes" : "no");
			map.put("checkEmail", teacher.getCheckEmail());
			map.put("checkIDCard",
					teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "yes" : "no");
			map.put("checkPhone", teacher.getCheckPhone());
			map.put("checkWork",
					teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "yes" : "no");
			map.put("createTime", teacher.getCreateTime() == null ? ""
					: SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(teacher.getCreateTime()))));
			map.put("email", teacher.getEmail());
			map.put("iconUrl", teacher.getIconUrl());
			map.put("introduce", teacher.getIntroduce());
			map.put("level", teacher.getLevel());
			map.put("name", teacher.getName());
			map.put("phone", teacher.getPhone());
			map.put("tid", teacher.getId());
			map.put("onService", teacher.getOnService());
			exTeachers.add(map.finishByJson());
		}
		String json = Json.getJson(exTeachers);
		setResMsg(MsgUtil.getSuccessMap().put("data", json).finishByJson());
	}

}
