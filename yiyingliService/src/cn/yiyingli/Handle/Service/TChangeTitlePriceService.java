package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TServiceService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeTitlePriceService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private TServiceService tServiceService;

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

	public TServiceService gettServiceService() {
		return tServiceService;
	}

	public void settServiceService(TServiceService tServiceService) {
		this.tServiceService = tServiceService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("teacherId") && getData().containsKey("time")
				&& getData().containsKey("price") && getData().containsKey("title");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Teacher teacher = getTeacherService().queryByUserIdWithTService(user.getId(), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("you are not a teacher"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsg("uid don't match teacherId"));
			return;
		}
		try {
			Float price = Float.valueOf((String) getData().get("price"));
			if (price < 0.01)
				price = 0.01F;
			Float time = Float.valueOf((String) getData().get("time"));
			String title = (String) getData().get("title");
			TService tService = teacher.gettService();
			tService.setTitle(title);
			tService.setTime(time);
			tService.setPriceTotal(price);
			gettServiceService().update(tService);
			setResMsg(MsgUtil.getSuccessMsg("TService has changed"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsg("input data is wrong"));
		}
	}

}
