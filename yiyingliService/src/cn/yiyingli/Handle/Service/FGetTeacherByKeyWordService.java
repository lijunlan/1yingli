package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class FGetTeacherByKeyWordService extends MsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("keyWord");
	}

	@Override
	public void doit() {
		String keyWord = getData().getString("keyWord");
		List<Teacher> teacherList = getTeacherService().queryListByKeyWord(keyWord, 1, false);
		ExList toSend = new ExArrayList();
		for (Teacher teacher : teacherList) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleDetailForUser(teacher, map);
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}
}
