package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TGetTServiceInfoService extends TMsgService {

	@Override
	public void doit() {
		Teacher teacher = getTeacher();

		ServicePro servicePro = teacher.getServicePros().get(0);

		SuperMap map = MsgUtil.getSuccessMap();
		map.put("address", teacher.getAddress());
		map.put("count", servicePro.getNumber());
		setResMsg(map.finishByJson());
	}

}
