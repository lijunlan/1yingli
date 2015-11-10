package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetAlipayNoService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String alipayNo = teacher.getAlipay();
		setResMsg(MsgUtil.getSuccessMap().put("alipayNo", alipayNo).finishByJson());
	}
}
