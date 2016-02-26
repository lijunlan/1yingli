package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TSMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Util.MsgUtil;

public class TRemoveServiceProService extends TSMsgService {

	@Override
	public void doit() {
		ServicePro servicePro = getServicePro();
		servicePro.setRemove(true);
		getServiceProService().updateAndPlusNumber(servicePro,true,true);
		setResMsg(MsgUtil.getSuccessMsg("remove servicePro successfully"));
	}

}
