package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;

public class GetPhoneNOService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		String phone = user.getPhone();
		setResMsg(MsgUtil.getSuccessMap().put("phone", phone).finishByJson());
	}

}
