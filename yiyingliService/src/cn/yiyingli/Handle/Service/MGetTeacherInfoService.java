package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MGetTeacherInfoService extends MMsgService {

	private TeacherService teacherService;

	private UserService userService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("username");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("12015"));
			return;
		}
		try {
			Teacher teacher = getTeacherService().queryAll(user.getTeacher().getId());
//			JSONObject t = new JSONObject();
//			ExTeacher.assembleDetailForManagerThroughJSONObject(teacher, t);
//
//			JSONObject toSend = new JSONObject();
//			toSend.put("teacher", t);
//			toSend.put("state", "success");
//			setResMsg(toSend.toString());
			SuperMap map = MsgUtil.getSuccessMap();
			ExTeacher.assembleDetailForManager(teacher, map);
			setResMsg(map.finishByJson());
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("31001"));
		}
	}

}
