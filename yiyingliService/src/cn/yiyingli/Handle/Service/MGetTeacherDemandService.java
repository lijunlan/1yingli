package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.TeacherDemand;
import cn.yiyingli.Service.TeacherDemandService;
import cn.yiyingli.Util.MsgUtil;

public class MGetTeacherDemandService extends MMsgService {

	private TeacherDemandService teacherDemandService;

	public TeacherDemandService getTeacherDemandService() {
		return teacherDemandService;
	}

	public void setTeacherDemandService(TeacherDemandService teacherDemandService) {
		this.teacherDemandService = teacherDemandService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = 0;
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("32009"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		List<TeacherDemand> list = teacherDemandService.queryListByTime(page);
		ExList sends = new ExArrayList();
		SuperMap map;
		for (TeacherDemand td : list) {
			map = new SuperMap();
			map.put("id", td.getId());
			map.put("createTime", td.getCreateTime());
			map.put("demand", td.getDemand());
			map.put("email", td.getEmail());
			map.put("ip", td.getIp());
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());
	}

}
