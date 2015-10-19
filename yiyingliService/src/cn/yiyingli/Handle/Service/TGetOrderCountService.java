package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class TGetOrderCountService extends MsgService {

	private TeacherService teacherService;

	private UserMarkService userMarkService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		String teacherId = (String) getData().get("teacherId");
		Teacher teacher = getTeacherService().query(Long.valueOf(teacherId), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("teahcerId is not existed"));
			return;
		}
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = teacher.getOrderNumber();
		if (count % CommentService.PAGE_SIZE_INT > 0)
			page = (int) (count / OrderService.PAGE_SIZE_INT) + 1;
		else
			page = (int) (count / OrderService.PAGE_SIZE_INT);
		if (page == 0)
			page = 1;
		toSend.put("page", page).put("count", count);
		setResMsg(toSend.finishByJson());
	}

}
