package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;


public class TGetLikeTeacherList extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		int page;
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		List<Teacher> teachers = getTeacherService().queryListByLikedTeacherId(teacher.getId(), page);
		long count = getTeacherService().querySumNoByLikedTeacherId(teacher.getId());
		toSend.put("count", count);
		ExList sends = new ExArrayList();
		for (Teacher t : teachers) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleSimpleForUser(teacher, map);
			sends.add(map.finish());
		}
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}
}
