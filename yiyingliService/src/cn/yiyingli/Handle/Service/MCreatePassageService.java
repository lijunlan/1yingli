package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PPassageUtil;

public class MCreatePassageService extends MMsgService {

	private PassageService passageService;

	private UserService userService;

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

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("username") && getData().containsKey("title")
				&& getData().containsKey("tag") && getData().containsKey("content") && getData().containsKey("imageUrl")
				&& getData().containsKey("summary");
	}

	@Override
	public void doit() {
		String username = (String) getData().get("username");
		User user = getUserService().queryWithTeacher(username, false);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		Teacher teacher = user.getTeacher();
		String title = (String) getData().get("title");
		String tag = (String) getData().get("tag");
		String content = (String) getData().get("content");
		String imageUrl = (String) getData().get("imageUrl");
		String summary = getData().getString("summary");
		String activityWeight = getData().getString("activityWeight");

		Passage passage = new Passage();
		PPassageUtil.toSavePassageByManager(teacher, title, tag, summary, content, imageUrl,
				(activityWeight == null ? passage.getActivityWeight() : Long.valueOf(activityWeight)), passage);

		getPassageService().save(passage, true);
		setResMsg(MsgUtil.getSuccessMsg("create passage successfully"));
	}

}
