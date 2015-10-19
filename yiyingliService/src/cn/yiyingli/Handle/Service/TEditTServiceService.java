package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TServiceService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class TEditTServiceService extends MsgService {

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
		return getData().containsKey("teacherId") && getData().containsKey("timeperweek")
				&& getData().containsKey("freetime") && getData().containsKey("talkWay")
				&& getData().containsKey("price") && getData().containsKey("time") && getData().containsKey("uid")
				&& getData().containsKey("address");
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
			int timeperweek = Integer.valueOf((String) getData().get("timeperweek"));
			String freetime = (String) getData().get("freetime");
			String talkWay = (String) getData().get("talkWay");
			float price = Float.valueOf(((String) getData().get("price")));
			float time = Float.valueOf(((String) getData().get("time")));
			String address = (String) getData().get("address");
			
			TService tService = teacher.gettService();
			tService.setTimesPerWeek(timeperweek);
			tService.setFreeTime(freetime);
			tService.setPriceTotal(price);
			tService.setTime(time);
			
			teacher.setAddress(address);
			teacher.setTalkWay(talkWay);
			getTeacherService().updateWithUser(teacher, user.getId());
			setResMsg(MsgUtil.getSuccessMsg("tservice info has changed"));
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("input data is wrong"));
		}
	}

}
