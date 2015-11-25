package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TServiceService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTServiceUtil;

public class TEditTServiceService extends TMsgService {

	private TServiceService tServiceService;

	public TServiceService gettServiceService() {
		return tServiceService;
	}

	public void settServiceService(TServiceService tServiceService) {
		this.tServiceService = tServiceService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("timeperweek") && getData().containsKey("talkWay")
				&& getData().containsKey("address");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		User user = getUser();
		try {
			int timeperweek = Integer.valueOf((String) getData().get("timeperweek"));
			// String freetime = (String) getData().get("freetime");
			String talkWay = (String) getData().get("talkWay");
			// float price = Float.valueOf(((String) getData().get("price")));
			// float time = Float.valueOf(((String) getData().get("time")));
			String address = (String) getData().get("address");

			TService tService = teacher.gettService();
			PTServiceUtil.editTimePerWeekByTeacher(timeperweek, tService);
			teacher.setAddress(address);
			teacher.setTalkWay(talkWay);
			getTeacherService().updateWithUser(teacher, user.getId());
			setResMsg(MsgUtil.getSuccessMsg("tservice info has changed"));
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("21001"));
		}
	}

}
