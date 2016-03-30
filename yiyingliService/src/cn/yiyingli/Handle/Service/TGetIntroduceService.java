package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetIntroduceService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();

		setResMsg(MsgUtil.getSuccessMap().put("introduce", teacher.getIntroduce()).finishByJson());
	}

}
