package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
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
		SuperMap toSend = MsgUtil.getSuccessMap();
		List<Teacher> teachers = getTeacherService().queryListBySale(p);
		long sum = getTeacherService().queryListBySaleNo();
		toSend.put("count", sum);
		ExList exTeachers = new ExArrayList();
		for (Teacher teacher : teachers) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleSimpleForUser(teacher, map);
			exTeachers.add(map.finish());
		}
		if (exTeachers.size() == TeacherService.SALE_PAGE_SIZE) {
			setResMsg(toSend.put("data", exTeachers).finishByJson());
		} else {
			setResMsg(toSend.put("data", exTeachers).put("page", "max").finishByJson());
		}
	}

}
