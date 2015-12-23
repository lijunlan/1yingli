package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TSMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;

public class TChangeTalkTitlePriceService extends TSMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("count") && getData().containsKey("price")
				&& getData().containsKey("title") && getData().containsKey("onsale")
				&& getData().containsKey("priceTemp") && getData().containsKey("numeral")
				&& getData().containsKey("quantifier") && getData().containsKey("onshow");
	}

	@Override
	public void doit() {
		try {
			float price = Float.valueOf(getData().getString("price"));
			float priceTemp = Float.valueOf(getData().getString("priceTemp"));
			if (price < 0.01) {
				price = 0.01F;
			}
			if (priceTemp < 0.01) {
				price = 0.01F;
			}
			float numeral = Float.valueOf(getData().getString("numeral"));
			String title = getData().getString("title");
			int count = getData().getInt("count");
			String quantifier = getData().getString("quantifier");
			boolean onsale = Boolean.valueOf(getData().getString("onsale"));
			boolean onshow = Boolean.valueOf(getData().getString("onshow"));
			ServicePro servicePro = getServicePro();
			PServiceProUtil.editPriceByTeacher(price, numeral, count, quantifier, title, onsale, priceTemp, onshow,
					servicePro);

			getServiceProService().update(servicePro);
			setResMsg(MsgUtil.getSuccessMsg("ServicePro has changed"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("21001"));
		}
	}

}
