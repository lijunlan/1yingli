package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class MGetPayInformationService extends MMsgService{

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacherService().query(getData().getLong("teacherId"));
		setResMsg(MsgUtil.getSuccessMap().put("paypal", teacher.getPaypal())
				.put("alipayNo", teacher.getAlipay())
				.put("teacherName", teacher.getName())
				.finishByJson());
	}
}
