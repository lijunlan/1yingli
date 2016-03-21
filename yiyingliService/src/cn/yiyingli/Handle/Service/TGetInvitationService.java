package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExTeacher;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.InvitationUtil;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class TGetInvitationService extends TMsgService {


	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String invitationCode = teacher.getInvitationCode();
		if (null == invitationCode) {
			do {
				invitationCode = InvitationUtil.createInvitationCode(teacher.getName());
			} while (getTeacherService().queryByInvitationCode(invitationCode) == null);
			teacher.setInvitationCode(invitationCode);
			getTeacherService().save(teacher);
		}
		int page;
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("12010"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("12010"));
			return;
		}
		long count = getTeacherService().querySumNoByInviterId(teacher.getId());
		List<Teacher> invitee = getTeacherService().queryListByInviterId(teacher.getId(), page);
		ExList sends = new ExArrayList();
		for (Teacher t : invitee) {
			SuperMap map = new SuperMap();
			ExTeacher.assembleSimpleForUser(t, map);
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("invitationCode", invitationCode);
		toSend.put("count", count);
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}
}
