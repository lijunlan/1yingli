package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;

public class GetUserInfoService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("address", user.getAddress());
		map.put("nickName", user.getNickName());
		map.put("name", user.getName());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		map.put("resume", user.getResume());
		setResMsg(map.finishByJson());
	}

}
