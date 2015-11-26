package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.List;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RecordService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class GetTeacherSimpleInfoListService extends MsgService {

	private TeacherService teacherService;

	private RecordService recordService;

	private UserMarkService userMarkService;

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		// getData().containsKey("uid")
		return getData().containsKey("tip");
	}

	@Override
	public void doit() {
		String tipId = (String) getData().get("tip");
		try {
			List<Teacher> teachers = getTeacherService().queryByTipOrderByShow(Long.valueOf(tipId), false);
			ExList exTeachers = new ExArrayList();
			for (Teacher teacher : teachers) {
				SuperMap map = ExTeacher.assembleSimpleForUser(teacher);
				exTeachers.add(map.finish());
			}
			saveRecord(tipId);
			setResMsg(MsgUtil.getSuccessMap().put("data", exTeachers).finishByJson());
		} catch (NumberFormatException e) {
			setResMsg(MsgUtil.getErrorMsgByCode("51001"));
			return;
		}
	}

	/**
	 * 为浏览添加记录
	 * 
	 * @param teacher
	 */
	private void saveRecord(String tid) {

		Record r = new Record();
		r.setKind(RecordService.RECORD_KIND_SEE_TIP);
		r.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		r.setIp((String) getData().get("IP"));

		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
					r.setType(RecordService.RECORD_TYPE_TEACHER);
					r.setData("tipId=" + tid + ",userId=" + user.getId());
				} else {
					r.setType(RecordService.RECORD_TYPE_USER);
					r.setData("tipId=" + tid + ",userId=" + user.getId());
				}
			} else {
				r.setType(RecordService.RECORD_TYPE_GUEST);
				r.setData("tipId=" + tid);
			}
		} else {
			r.setType(RecordService.RECORD_TYPE_GUEST);
			r.setData("tipId=" + tid);
		}

		getRecordService().save(r);
	}

}