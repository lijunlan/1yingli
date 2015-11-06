package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;

public class GetEmailService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		String email = user.getEmail();
		setResMsg(MsgUtil.getSuccessMap().put("email", email).finishByJson());
	}

}