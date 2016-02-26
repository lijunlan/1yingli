package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import net.sf.json.JSONObject;

public class MEditServiceProService extends MMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("servicePro") && getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"), false);
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42002"));
			return;
		}
		JSONObject jsonServicePro = getData().getJSONObject("servicePro");
		int count = jsonServicePro.getInt("count");
		float price = Float.valueOf(jsonServicePro.getString("price"));
		float priceTemp = Float.valueOf(jsonServicePro.getString("priceTemp"));
		int numeral = jsonServicePro.getInt("numeral");
		String freeTime = jsonServicePro.getString("freeTime");
		String tip = jsonServicePro.getString("tip");
		String onshow = jsonServicePro.getString("onshow");
		String onsale = jsonServicePro.getString("onsale");
		String quantifier = jsonServicePro.getString("quantifier");
		String servicetitle = jsonServicePro.getString("title");
		String servicecontent = jsonServicePro.getString("content");
		String imageUrls = jsonServicePro.getString("imageUrls");
		String summary = jsonServicePro.getString("summary");
		int kind = jsonServicePro.getInt("kind");
		int saleWeight = jsonServicePro.getInt("saleWeight");
		int homeWeight = jsonServicePro.getInt("homeWeight");
		String address = jsonServicePro.getString("address");
		short talkWay = (short) jsonServicePro.getInt("talkWay");
		PServiceProUtil.editrByManager(count, price, priceTemp, numeral, kind, talkWay, address, freeTime, tip, onshow,
				onsale, quantifier, servicetitle, servicecontent, imageUrls, summary, homeWeight, saleWeight,
				servicePro);
		getServiceProService().update(servicePro);
		setResMsg(MsgUtil.getSuccessMsg("edit servicePro successfully"));
	}

}
