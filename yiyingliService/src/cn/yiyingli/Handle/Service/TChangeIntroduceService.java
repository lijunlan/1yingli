package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TChangeIntroduceService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("introduce");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String introduce = (String) getData().get("introduce");
		teacher.setIntroduce(introduce);
		getTeacherService().update(teacher,false);
		setResMsg(MsgUtil.getSuccessMsg("introduce has changed"));
	}

}
