package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TChangeSimpleInfoService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("simpleinfo");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String simpleinfo = (String) getData().get("simpleinfo");
		teacher.setSimpleInfo(simpleinfo);
		getTeacherService().update(teacher);
		setResMsg(MsgUtil.getSuccessMsg("simpleinfo has changed"));

	}

}
