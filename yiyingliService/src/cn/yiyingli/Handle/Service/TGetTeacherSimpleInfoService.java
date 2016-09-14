package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.LikeAndFinishNoShowUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetTeacherSimpleInfoService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("iconUrl", teacher.getIconUrl());
		map.put("name", teacher.getName());
		map.put("simpleinfo", teacher.getSimpleInfo());
		LikeAndFinishNoShowUtil.setLikeNo(teacher, map);
		setResMsg(map.finishByJson());
	}

}
