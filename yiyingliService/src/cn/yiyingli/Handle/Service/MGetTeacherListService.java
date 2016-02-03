package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
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
		return super.checkData() && getData().containsKey("page") || getData().containsKey("name");
	}

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		if (Integer.valueOf(page) <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		List<Teacher> teachers = null;
		if (getData().containsKey("name")) {
			teachers = getTeacherService().queryByNameOrUsername(getData().getString("name"));
		} else {
			teachers = getTeacherService().queryList(Integer.valueOf(page), false);
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
