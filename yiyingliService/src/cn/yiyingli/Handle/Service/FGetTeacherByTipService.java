package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class FGetTeacherByTipService extends MsgService {

	private TeacherService teacherService;

	private TipService tipService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("tip");
	}

	@Override
	public void doit() {
		Long tipId = getData().getLong("tip");
		List<Teacher> teacherList = getTeacherService().queryListByTip(tipId, 1, TeacherService.PAGE_SIZE_INT);
		ExList toSend = new ExArrayList();
		for (Teacher teacher : teacherList) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleDetailForUser(teacher, map);
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}
}
