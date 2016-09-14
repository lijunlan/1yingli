package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RecordService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

public class GetTeacherAllInfoService extends MsgService {

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
		return getData().containsKey("teacherId");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacherService().queryForUser(Long.valueOf((String) getData().get("teacherId")));
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();

		ExTeacher.assembleDetailForUser(teacher, map);

		saveRecord(teacher);

		setResMsg(map.finishByJson());
	}

	/**
	 * 为浏览添加记录
	 * 
	 * @param teacher
	 */
	private void saveRecord(Teacher teacher) {

		getTeacherService().updateAddLookNumber(teacher.getId(), 1L);

		Record r = new Record();
		r.setKind(RecordService.RECORD_KIND_SEE_TEACHER);
		r.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		r.setIp((String) getData().get("IP"));

		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
					r.setType(RecordService.RECORD_TYPE_TEACHER);
					r.setData("teacherId=" + teacher.getId() + ",userId=" + user.getId());
				} else {
					r.setType(RecordService.RECORD_TYPE_USER);
					r.setData("teacherId=" + teacher.getId() + ",userId=" + user.getId());
				}
				if ("false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
					SendMsgToBaiduUtil.updateUserTrainDataLike(user.getId() + "", teacher.getId() + "",
							Calendar.getInstance().getTimeInMillis() + "");
				}
			} else {
				r.setType(RecordService.RECORD_TYPE_GUEST);
				r.setData("teacherId=" + teacher.getId());
			}
		} else {
			r.setType(RecordService.RECORD_TYPE_GUEST);
			r.setData("teacherId=" + teacher.getId());
		}

		getRecordService().save(r);
	}

}
