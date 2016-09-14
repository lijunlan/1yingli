package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class MSearchTeacherService extends MMsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && (getData().containsKey("teacherId") || getData().containsKey("word"));
	}

	@Override
	public void doit() {
		List<Teacher> teachers = null;
		if (getData().containsKey("teacherId")) {
			long id = Long.valueOf((String) getData().get("teacherId"));
			List<Long> ids = new ArrayList<Long>();
			ids.add(id);
			teachers = getTeacherService().queryByIds(ids);
		} else {
			String word = (String) getData().get("word");
			teachers = getTeacherService().queryByNameOrUsername(word);
		}
		ExList exTeachers = new ExArrayList();
		for (Teacher teacher : teachers) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleSimpleForManager(teacher, map);
			exTeachers.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", exTeachers).finishByJson());
	}

}
