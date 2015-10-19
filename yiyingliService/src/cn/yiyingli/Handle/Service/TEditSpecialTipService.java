package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class TEditSpecialTipService extends MsgService {

	private UserMarkService userMarkService;

	private TeacherService teacherService;

	private TipService tipService;

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

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId")&&getData().containsKey("uid")&&getData().containsKey("tips");
	}

	@SuppressWarnings("unchecked")
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
		List<Object> tips = (List<Object>) getData().get("tips");
		teacher.getTips().clear();
		for (Object t : tips) {
			Long tid = Long.valueOf((String) ((Map<String, Object>) t).get("id"));
			Tip mT = getTipService().query(tid);
			teacher.getTips().add(mT);
		}
		getTeacherService().update(teacher);
		setResMsg(MsgUtil.getSuccessMsg("tips has changed"));
	}

}
