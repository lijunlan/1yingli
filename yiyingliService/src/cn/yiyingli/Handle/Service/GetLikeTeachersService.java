package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SimpleShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class GetLikeTeachersService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		if (getData().get("page").equals("max")) {
			long count = user.getLikeTeacherNumber();
			if (count % TeacherService.PAGE_SIZE_INT > 0)
				page = (int) (count / TeacherService.PAGE_SIZE_INT) + 1;
			else
				page = (int) (count / TeacherService.PAGE_SIZE_INT);
			if (page == 0)
				page = 1;
			toSend.put("page", page);
		} else {
			try {
				page = Integer.parseInt((String) getData().get("page"));
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsg("page is wrong"));
				return;
			}
			if (page <= 0) {
				setResMsg(MsgUtil.getErrorMsg("page is wrong"));
				return;
			}
		}
		List<Teacher> teachers = getTeacherService().queryLikeListByUserId(user.getId(), page, false);
		List<String> sends = new ArrayList<String>();
		for (Teacher t : teachers) {
			SuperMap map = new SuperMap();
			map.put("name", t.getName());
			map.put("teacherId", t.getId());
			map.put("iconUrl", t.getIconUrl());
			map.put("price", t.gettService().getPriceTotal());
			map.put("time", t.gettService().getTime());
			map.put("title", t.gettService().getTitle());
			map.put("timeperweek", t.gettService().getTimesPerWeek());
			SimpleShowUtil.getSimpleShowByTip(t, map);
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
	}

}
