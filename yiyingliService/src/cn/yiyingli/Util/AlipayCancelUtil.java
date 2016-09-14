package cn.yiyingli.Util;

import java.util.HashMap;
import java.util.Map;
import cn.yiyingli.Alipay.*;

public class AlipayCancelUtil {

	/**
	 * trade_no是支付宝交易号，out_order_no是订单号，二者必选其一 trade_no的理论值是""
	 * 
	 * @param trade_no
	 * @param out_order_no
	 * @throws Exception
	 */
	public static boolean cancelOrder(String trade_no, String out_order_no) throws Exception {

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "close_trade");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", trade_no);
		sParaTemp.put("out_order_no", out_order_no);

		// 建立请求
		String sHtmlText;

		sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		// 处理xml
		AlipayXmlProcess axp = AlipayXmlProcess.getInstance();
		return axp.tradeCancelStringProcess(sHtmlText);

	}
}
