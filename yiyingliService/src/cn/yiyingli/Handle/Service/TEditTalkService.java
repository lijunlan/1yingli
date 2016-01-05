package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;

public class TEditTalkService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("address");
	}

	@Override
	public void doit() {
		String address = getData().getString("address");
		Teacher teacher = getTeacher();
		User user = getUser();
		teacher.setAddress(address);
		getTeacherService().updateWithUser(teacher, user.getId(), false);
		setResMsg(MsgUtil.getSuccessMsg("servicePro and teacher Info has changed"));
	}

}
