package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TEditPayPalService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("paypalNo");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String paypalNo = (String) getData().get("paypalNo");
		teacher.setPaypal(paypalNo);
		getTeacherService().update(teacher, false);
		setResMsg(MsgUtil.getSuccessMsg("alipayNo has changed"));
	}

}
