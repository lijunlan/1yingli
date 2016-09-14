package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.TeacherDemand;
import cn.yiyingli.Service.TeacherDemandService;
import cn.yiyingli.Util.MsgUtil;

public class FTeacherDemandService extends MsgService {

	private TeacherDemandService teacherDemandService;

	public TeacherDemandService getTeacherDemandService() {
		return teacherDemandService;
	}

	public void setTeacherDemandService(TeacherDemandService teacherDemandService) {
		this.teacherDemandService = teacherDemandService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("email") && getData().containsKey("demand");
	}

	@Override
	public void doit() {
		String email = (String) getData().get("email");
		String demand = (String) getData().get("demand");
		String IP = (String) getData().get("IP");
		TeacherDemand teacherDemand = new TeacherDemand();
		teacherDemand.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		teacherDemand.setDemand(demand);
		teacherDemand.setEmail(email);
		teacherDemand.setIp(IP);
		getTeacherDemandService().save(teacherDemand);
		setResMsg(MsgUtil.getSuccessMsg("your demand has been accepted"));
	}

}
