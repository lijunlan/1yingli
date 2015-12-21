package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetPaypalNoService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String paypal = teacher.getPaypal();
		setResMsg(MsgUtil.getSuccessMap().put("paypal", paypal).finishByJson());
	}
}
