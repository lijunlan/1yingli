package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FGetRecommendServiceProListService extends MsgService {

	private UserMarkService userMarkService;

	private ServiceProService serviceProService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		String serviceProId = getData().getString("serviceProId");
		String result = SendMsgToBaiduUtil.getRecommendServiceProListAbout(serviceProId);
		getServiceProInfo(result);
	}

	private void getServiceProInfo(String result) {
		JSONObject r = JSONObject.fromObject(result);
		if (r.getInt("Code") != 300 && r.getInt("Code") != 301 && r.getInt("Code") != 302 && r.getInt("Code") != 303
				&& r.getInt("Code") != 304) {
			setResMsg(MsgUtil.getErrorMsgByCode("53001"));
			return;
		}
		JSONArray ra = r.getJSONArray("Results");
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < ra.size(); i++) {
			JSONObject jobj = ra.getJSONObject(i);
			String pid = jobj.getString("ItemId");
			ids.add(Long.valueOf(pid));
		}
		List<ServicePro> servicePros = null;
		if (ids.size() > 0) {
			servicePros = getServiceProService().queryListByIds(ids);
			if (servicePros.size() == 0) {
				servicePros = getServiceProService().queryListByRecommand(0, ServiceProService.STATE_OK, true);
			}
		} else {
			servicePros = getServiceProService().queryListByRecommand(0, ServiceProService.STATE_OK, true);
		}
		SuperMap map = MsgUtil.getSuccessMap();
		JSONArray jsonServicePros = new JSONArray();
		for (ServicePro servicePro : servicePros) {
			if (servicePro.getId().longValue() == getData().getLong("serviceProId")) {
				continue;
			}
			SuperMap m = new SuperMap();
			ExServicePro.assembleSimpleServiceProForUser(servicePro, m);
			jsonServicePros.add(m.finish());
		}
		map.put("data", jsonServicePros.toString());
		setResMsg(map.finishByJson());
	}
}