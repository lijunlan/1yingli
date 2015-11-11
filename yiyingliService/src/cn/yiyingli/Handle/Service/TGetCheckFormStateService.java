package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class TGetCheckFormStateService extends MsgService {

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
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		if (teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_SUCCESS_SHORT) {
			map.put("idcardState", "success");
		} else if (teacher.getCheckIDCardState() == TeacherService.CHECK_STATE_CHECKING_SHORT) {
			map.put("idcardState", "checking");
		} else {
			map.put("idcardState", "none");
		}

		if (teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_SUCCESS_SHORT) {
			map.put("studyState", "success");
		} else if (teacher.getCheckDegreeState() == TeacherService.CHECK_STATE_CHECKING_SHORT) {
			map.put("studyState", "checking");
		} else {
			map.put("studyState", "none");
		}

		if (teacher.getCheckWorkState() == TeacherService.CHECK_STATE_SUCCESS_SHORT) {
			map.put("workState", "success");
		} else if (teacher.getCheckWorkState() == TeacherService.CHECK_STATE_CHECKING_SHORT) {
			map.put("workState", "checking");
		} else {
			map.put("workState", "none");
		}

		setResMsg(map.finishByJson());
	}

}
