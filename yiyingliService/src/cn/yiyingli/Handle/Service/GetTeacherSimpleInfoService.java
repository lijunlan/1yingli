package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class GetTeacherSimpleInfoService extends MsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacherService().query(Long.valueOf((String) getData().get("teacherId")));
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExTeacher.assembleSimpleForUser(teacher, map);

		setResMsg(map.finishByJson());
	}

}
