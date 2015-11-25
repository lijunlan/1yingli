package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExUser;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;

public class GetUserInfoService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		SuperMap map = MsgUtil.getSuccessMap();
		ExUser.assembleUserInfo(user, map);
		setResMsg(map.finishByJson());
	}

}
