package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class MGetPassageListService extends MMsgService {

	private UserService userService;

	private TeacherService teacherService;

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public boolean checkData() {
		// contain state
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = Integer.valueOf((String) getData().get("page"));
		List<Passage> passages;
		if (!getData().containsKey("state")) {
			if (getData().containsKey("username")) {
				String username = (String) getData().get("username");
				User user = getUserService().queryWithTeacher(username, false);
				Long teacherId;
				if (user != null) {
					teacherId = user.getTeacher().getId();
				} else {
					Teacher teacher = getTeacherService().queryByName(username);
					if (teacher == null) {
						try {
							teacher = getTeacherService().query(Long.parseLong(username));
						} catch (NumberFormatException e) {
							setResMsg(MsgUtil.getErrorMsgByCode("32015"));
							return;
						}
					}
					teacherId = teacher.getId();
				}
				passages = getPassageService().queryListByTeacher(page,teacherId);
			} else {
				passages = getPassageService().queryList(page);
			}
		} else {
			short state = Short.valueOf((String) getData().get("state"));
			passages = getPassageService().queryListByState(page, state);
		}
		ExList sends = new ExArrayList();
		for (Passage p : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleForManager(p, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}

}
