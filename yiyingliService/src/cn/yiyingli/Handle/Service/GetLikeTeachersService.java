package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacherSimpleShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class GetLikeTeachersService extends UMsgService {

	private TeacherService teacherService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		User user = getUser();
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = user.getLikeTeacherNumber();
		toSend.put("count", count);
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("12010"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("12010"));
			return;
		}
		List<Teacher> teachers = getTeacherService().queryLikeListByUserId(user.getId(), page, false);
		ExList sends = new ExArrayList();
		for (Teacher t : teachers) {
			SuperMap map = new SuperMap();
			map.put("name", t.getName());
			map.put("teacherId", t.getId());
			map.put("iconUrl", t.getIconUrl());
			map.put("price", t.gettService().getPriceTotal());
			map.put("time", t.gettService().getTime());
			map.put("title", t.gettService().getTitle());
			map.put("timeperweek", t.gettService().getTimesPerWeek());
			ExTeacherSimpleShowUtil.getSimpleShowByTip(t, map);
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());
	}

}
