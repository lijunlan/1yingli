package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TChangeTalkTitlePriceService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("price") && getData().containsKey("topic");
	}

	@Override
	public void doit() {
		float price = Float.valueOf(getData().getString("price"));
		String topic = getData().getString("topic");
		Teacher teacher = getTeacher();
		teacher.setTopic(topic);
		teacher.setPrice(price);
		getTeacherService().update(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("teacher topic has changed"));
		// try {
		// float price = Float.valueOf(getData().getString("price"));
		// float priceTemp = Float.valueOf(getData().getString("priceTemp"));
		// if (price < 0.01) {
		// price = 0.01F;
		// }
		// if (priceTemp < 0.01) {
		// price = 0.01F;
		// }
		// float numeral = Float.valueOf(getData().getString("numeral"));
		// String title = getData().getString("title");
		// int count = getData().getInt("count");
		// String quantifier = getData().getString("quantifier");
		// boolean onsale = Boolean.valueOf(getData().getString("onsale"));
		// boolean onshow = Boolean.valueOf(getData().getString("onshow"));
		// ServicePro servicePro = getServicePro();
		// PServiceProUtil.editPriceByTeacher(price, numeral, count, quantifier,
		// title, onsale, priceTemp, onshow,
		// servicePro);
		//
		// getServiceProService().update(servicePro);
		// } catch (Exception e) {
		// setResMsg(MsgUtil.getErrorMsgByCode("21001"));
		// }
	}

}
