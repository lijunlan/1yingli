package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class GetUserSimpleInfoService extends UMsgService {

	@Override
	public void doit() {
		super.doit();
		User user = getUser();
		setResMsg(MsgUtil.getSuccessMap().put("nickName", user.getNickName()).put("iconUrl", user.getIconUrl())
				.put("isTeacher", user.getTeacherState() == TeacherService.CHECK_STATE_NONE_SHORT ? "no" : "yes")
				.finishByJson());
	}

}
