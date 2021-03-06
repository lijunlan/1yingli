package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Service.RecordService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import cn.yiyingli.Util.TimeTaskUtil;

public class FGetPassageService extends MsgService {

	private PassageService passageService;

	private RecordService recordService;

	private UserMarkService userMarkService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
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
		// or contain uid
		return getData().containsKey("pid");
	}

	@Override
	public void doit() {
		Passage passage = getPassageService().queryByUserWithTeacher(Long.valueOf((String) getData().get("pid")));
		if (passage == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22006"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExPassage.assembleDetail(passage, map);

		saveRecord(passage);

		setResMsg(map.finishByJson());
	}

	/**
	 * 为浏览添加记录
	 * 
	 * @param passage
	 */
	private void saveRecord(Passage passage) {
		TimeTaskUtil.sendTimeTask("add", "passage", (Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 10) + "",
				new SuperMap().put("passageId", passage.getId()).put("number", 1).finishByJson());
		Record r = new Record();
		r.setKind(RecordService.RECORD_KIND_SEE_PASSAGE);
		r.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		r.setIp((String) getData().get("IP"));

		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
					r.setType(RecordService.RECORD_TYPE_TEACHER);
					r.setData("passageId=" + passage.getId() + ",userId=" + user.getId());
				} else {
					r.setType(RecordService.RECORD_TYPE_USER);
					r.setData("passageId=" + passage.getId() + ",userId=" + user.getId());
				}
				if ("false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
					SendMsgToBaiduUtil.updatePassageUserTrainDataRead(user.getId() + "", passage.getId() + "",
							Calendar.getInstance().getTimeInMillis() + "");
				}
			} else {
				r.setType(RecordService.RECORD_TYPE_GUEST);
				r.setData("passageId=" + passage.getId());
			}
		} else {
			r.setType(RecordService.RECORD_TYPE_GUEST);
			r.setData("passageId=" + passage.getId());
		}

		getRecordService().save(r);
	}

}
