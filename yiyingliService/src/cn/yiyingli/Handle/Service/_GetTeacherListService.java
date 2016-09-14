package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.TeacherService;

/**
 * @author sdll18
 *
 */
public class _GetTeacherListService extends MsgService {

	private TeacherService teacherService;

	private ManagerMarkService managerMarkService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

	}

}
