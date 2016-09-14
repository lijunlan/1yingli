package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class TGetTeacherCheckInfoService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
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
