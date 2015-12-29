package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetTalkService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();

		ServicePro servicePro = teacher.getServicePros().get(0);
		SuperMap map = MsgUtil.getSuccessMap();
		ExServicePro.assembleSimpleTalkForTeacher(servicePro, map);
		setResMsg(map.finishByJson());
	}

}
