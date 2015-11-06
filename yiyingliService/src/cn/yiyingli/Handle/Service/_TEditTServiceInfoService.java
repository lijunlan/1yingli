package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TServiceService;
import cn.yiyingli.Util.MsgUtil;

public class _TEditTServiceInfoService extends TMsgService {

	private TServiceService tServiceService;

	public TServiceService gettServiceService() {
		return tServiceService;
	}

	public void settServiceService(TServiceService tServiceService) {
		this.tServiceService = tServiceService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("address") && getData().containsKey("timeperweek")
				&& getData().containsKey("freetime") && getData().containsKey("talkWay");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		try {
			String address = (String) getData().get("address");
			int timeperweek = Integer.valueOf((String) getData().get("timeperweek"));
			String freetime = (String) getData().get("freetime");
			String talkWay = (String) getData().get("talkWay");
			TService tService = teacher.gettService();
			tService.setTimesPerWeek(timeperweek);
			tService.setFreeTime(freetime);
			teacher.setAddress(address);
			teacher.setTalkWay(talkWay);
			getTeacherService().update(teacher);
			setResMsg(MsgUtil.getSuccessMsg("tservice info has changed"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("21001"));
		}
	}
}
