package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TEditAlipayNoService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("alipayNo");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String alipayNo = (String) getData().get("alipayNo");
		teacher.setAlipay(alipayNo);
		getTeacherService().update(teacher);
		setResMsg(MsgUtil.getSuccessMsg("alipayNo has changed"));
	}

}
