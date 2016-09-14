package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetBGService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		setResMsg(MsgUtil.getSuccessMap().put("bgurl", teacher.getBgUrl()).finishByJson());
	}

}
