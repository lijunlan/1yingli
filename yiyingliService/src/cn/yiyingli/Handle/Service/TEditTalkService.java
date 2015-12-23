package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;

public class TEditTalkService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("count") && getData().containsKey("address");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		User user = getUser();
		try {
			int count = getData().getInt("count");
			String address = getData().getString("address");

			ServicePro servicePro = teacher.getServicePros().get(0);
			PServiceProUtil.editCountByTeacher(count, servicePro);
			teacher.setAddress(address);
			getTeacherService().updateWithUser(teacher, user.getId(), false);
			setResMsg(MsgUtil.getSuccessMsg("servicePro and teacher Info has changed"));
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("21001"));
		}
	}

}
