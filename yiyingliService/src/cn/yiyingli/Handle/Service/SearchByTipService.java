package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExDataToShow;
import cn.yiyingli.ExchangeData.ExSearchTeacher;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class SearchByTipService extends MsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("tips") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		String json = (String) getData().get("tips");
		int page = Integer.valueOf((String) getData().get("page"));
		List<String> tips = Json.getStringArr(json);
		int tipmark = 0;
		for (String t : tips) {
			tipmark += Integer.valueOf(t);
		}
		List<Teacher> list = getTeacherService().queryListByTip(page, tipmark, false);
		List<ExDataToShow<Teacher>> exList = new ArrayList<ExDataToShow<Teacher>>();
		for (Teacher t : list) {
			ExSearchTeacher tmp = new ExSearchTeacher();
			tmp.setUpByPersistant(t);
			exList.add(tmp);
		}
		String msg = null;
		try {
			msg = Json.getJsonByEx(exList);
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("50001"));
			return;
		}
		setResMsg(msg);
	}

}
