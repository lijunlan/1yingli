package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CheckNoService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.MsgUtil;

public class CheckPhoneService extends MsgService {

	private UserMarkService userMarkService;

	private UserService userService;
	
	private TeacherService teacherService;

	private CheckNoService checkNoService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
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

	public CheckNoService getCheckNoService() {
		return checkNoService;
	}

	public void setCheckNoService(CheckNoService checkNoService) {
		this.checkNoService = checkNoService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("checkNo") && getData().containsKey("phone") && getData().containsKey("uid")&&getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Teacher teacher = getTeacherService().queryByUserId(user.getId(), false);
		if(teacher==null){
			setResMsg(MsgUtil.getErrorMsg("you are not a teacher"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		if(!teacherId.equals(teacher.getId()+"")){
			setResMsg(MsgUtil.getErrorMsg("uid don't match teacherId"));
			return;
		}
		String phone = (String) getData().get("phone");
		String checkNo = (String) getData().get("checkNo");
		CheckNo no = getCheckNoService().query(phone);
		long time = Calendar.getInstance().getTimeInMillis();
		if (no == null || !(no.getCheckNo().equals(checkNo))) {
			setResMsg(MsgUtil.getErrorMsg("checkNo is wrong"));
			return;
		} else if (time > Long.valueOf(no.getEndTime())) {
			setResMsg(MsgUtil.getErrorMsg("checkNo is overdue"));
			getCheckNoService().remove(no);
			return;
		} else {
			getCheckNoService().remove(no);
		}
		if (CheckUtil.checkMobileNumber(phone)) {
			if(teacher.getPhone().equals(phone)){
				teacher.setCheckPhone(true);
			}else{
				setResMsg(MsgUtil.getErrorMsg("phone number is wrong"));
				return;
			}
			getTeacherService().update(teacher);
			setResMsg(MsgUtil.getSuccessMsg("phone number has been checked"));
		} else {
			setResMsg(MsgUtil.getErrorMsg("phone number is not accurate"));
		}
	}

}
