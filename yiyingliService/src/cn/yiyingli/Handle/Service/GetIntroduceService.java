package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;

public class GetIntroduceService extends UMsgService {

	@Override
	public void doit() {
		User user = getUser();
		String introduce = user.getOresume();
		setResMsg(MsgUtil.getSuccessMap().put("introduce", introduce).put("name", user.getOname())
				.put("phone", user.getOphone()).put("email", user.getOemail()).put("contact", user.getContact())
				.put("question", user.getOquestion()).put("selectTime", user.getOtime()).finishByJson());
	}

}
