package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class TGetTeacherCheckInfoService extends MsgService {

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
		String teacherId = (String) getData().get("teacherId");
		Teacher teacher = getTeacherService().query(Long.valueOf(teacherId), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("teahcerId is not existed"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("checkDegree",
				teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
		map.put("checkEmail", teacher.getCheckEmail());
		map.put("checkIDCard",
				teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
		map.put("checkPhone", teacher.getCheckPhone());
		map.put("checkWork",
				teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT ? "true" : "false");
		setResMsg(map.finishByJson());
	}

}
