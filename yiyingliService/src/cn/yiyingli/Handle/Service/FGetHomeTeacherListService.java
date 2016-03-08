package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Service.TeacherService;
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
		List<Teacher> teachers = getTeacherService().queryListByActivity(PagesService.KEY_HOME_TEACHER, 0,
				TeacherService.HOME_PAGE_SIZE);
		ExList exTeachers = new ExArrayList();
		for (Teacher teacher : teachers) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleSimpleForUser(teacher, map);
			exTeachers.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", exTeachers).finishByJson());
	}

}
