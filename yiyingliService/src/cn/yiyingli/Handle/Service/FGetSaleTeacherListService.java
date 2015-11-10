package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacherNormal;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class FGetSaleTeacherListService extends MsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("page");
	}

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		int p = 0;
		try {
			p = Integer.parseInt(page);
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("51001"));
			return;
		}
		List<Teacher> teachers = getTeacherService().queryListBySale(p);
		List<String> exTeachers = new ArrayList<String>();
		for (Teacher teacher : teachers) {
			SuperMap map = ExTeacherNormal.assembleSimpleTeacher(teacher);
			exTeachers.add(map.finishByJson());
		}
		if (exTeachers.size() == TeacherService.SALE_PAGE_SIZE) {
			setResMsg(MsgUtil.getSuccessMap().put("data", Json.getJson(exTeachers)).finishByJson());
		} else {
			setResMsg(MsgUtil.getSuccessMap().put("data", Json.getJson(exTeachers)).put("page", "max").finishByJson());
		}
	}

}
