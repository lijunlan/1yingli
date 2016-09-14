package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Util.MsgUtil;

public class MLogoutService extends MMsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String UUID = (String) getData().get("mid");
		getManagerMarkService().remove(UUID);
		setResMsg(MsgUtil.getSuccessMsg("logout successfully"));
	}
}
