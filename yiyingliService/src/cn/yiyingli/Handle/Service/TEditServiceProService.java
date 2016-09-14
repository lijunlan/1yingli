package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TSMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import net.sf.json.JSONObject;

public class TEditServiceProService extends TSMsgService {

	public boolean checkData() {
		return super.checkData() && getData().containsKey("servicePro");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServicePro();
		JSONObject jsonServicePro = getData().getJSONObject("servicePro");
		PServiceProUtil.assembleByTeacherEdit(jsonServicePro, servicePro);
		getServiceProService().updateAndPlusNumber(servicePro, false,true);
		NotifyUtil.notifyManager(new SuperMap().put("type", "checkServicePro").finishByJson());
		setResMsg(MsgUtil.getSuccessMsg("edit servicePro successfully"));
	}

}
