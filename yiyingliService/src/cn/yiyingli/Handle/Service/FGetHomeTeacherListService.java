package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class FGetHomeTeacherListService extends MsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return true;
	}

	@Override
	public void doit() {
		List<Teacher> teachers = getTeacherService().queryListByHomePage();
		List<String> exTeachers = new ArrayList<String>();
		for (Teacher teacher : teachers) {
			SuperMap map = ExTeacher.assembleSimpleTeacher(teacher);
			exTeachers.add(map.finishByJson());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", Json.getJson(exTeachers)).finishByJson());
	}

}
