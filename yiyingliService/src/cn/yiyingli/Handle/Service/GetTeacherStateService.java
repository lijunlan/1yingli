package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Util.MsgUtil;

public class GetTeacherStateService extends UMsgService {

	@Override
	public void doit() {
		setResMsg(MsgUtil.getSuccessMap().put("teacherState", getUser().getTeacherState()).finishByJson());
	}

}
