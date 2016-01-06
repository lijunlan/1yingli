package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;

public class MEditServiceProService extends MMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("serviceProId") && getData().containsKey("count")
				&& getData().containsKey("price") && getData().containsKey("priceTemp")
				&& getData().containsKey("numeral") && getData().containsKey("tip") && getData().containsKey("onshow")
				&& getData().containsKey("onsale") && getData().containsKey("quantifier")
				&& getData().containsKey("title") && getData().containsKey("content")
				&& getData().containsKey("imageUrls") && getData().containsKey("summary")
				&& getData().containsKey("state") && getData().containsKey("kind")
				&& getData().containsKey("saleWeight") && getData().containsKey("homeWeight");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42002"));
			return;
		}
		int count = getData().getInt("count");
		float price = Float.valueOf(getData().getString("price"));
		float priceTemp = Float.valueOf(getData().getString("priceTemp"));
		int numeral = getData().getInt("numeral");
		// String freeTime = getData().getString("freeTime");
		String tip = getData().getString("tip");
		String onshow = getData().getString("onshow");
		String onsale = getData().getString("onsale");
		String quantifier = getData().getString("quantifier");
		String servicetitle = getData().getString("title");
		String servicecontent = getData().getString("content");
		String imageUrls = getData().getString("imageUrls");
		String summary = getData().getString("summary");
		short state = Short.valueOf(getData().getString("state"));
		int kind = getData().getInt("kind");
		int saleWeight = getData().getInt("saleWeight");
		int homeWeight = getData().getInt("homeWeight");
		PServiceProUtil.editrByManager(state, count, price, priceTemp, numeral, kind, "", tip, onshow, onsale,
				quantifier, servicetitle, servicecontent, imageUrls, summary, homeWeight, saleWeight, servicePro);
		getServiceProService().update(servicePro);
		setResMsg(MsgUtil.getSuccessMsg("edit servicePro successfully"));
	}

}
