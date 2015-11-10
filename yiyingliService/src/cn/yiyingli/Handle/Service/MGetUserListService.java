package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetUserListService extends MMsgService {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		if (Integer.valueOf(page) <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		List<User> users = getUserService().queryList(Integer.valueOf(page), false);
		List<String> exUsers = new ArrayList<String>();
		for (User user : users) {
			SuperMap map = new SuperMap();
			map.put("address", user.getAddress());
			map.put("creatTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(user.getCreateTime()))));
			map.put("email", user.getEmail());
			map.put("iconUrl", user.getIconUrl());
			map.put("name", user.getName());
			map.put("nickName", user.getNickName());
			map.put("phone", user.getPhone());
			map.put("resume", user.getResume());
			map.put("isTeacher", user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT ? "yes" : "no");
			map.put("username", user.getUsername());
			exUsers.add(map.finishByJson());
		}
		String json = Json.getJson(exUsers);
		setResMsg(MsgUtil.getSuccessMap().put("data", json).finishByJson());
	}

}
