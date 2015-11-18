package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class FGetPassageListService extends MsgService {

	private PassageService passageService;

	private TeacherService teacherService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		long tid = Long.valueOf((String) getData().get("teacherId"));
		Teacher teacher = getTeacherService().query(tid, false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		int page = Integer.valueOf((String) getData().get("page"));
		List<Passage> passages = getPassageService().queryListByTeacherAndState(page, tid,
				PassageService.PASSAGE_STATE_OK);
		List<String> sends = new ArrayList<String>();
		for (Passage p : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleSimple(p, map);
			sends.add(map.finishByJson());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", teacher.getPassageNumber());
		toSend.put("data", Json.getJson(sends));
		setResMsg(toSend.finishByJson());

	}

}
