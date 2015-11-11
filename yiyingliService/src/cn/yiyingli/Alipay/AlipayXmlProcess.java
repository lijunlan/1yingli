package cn.yiyingli.Alipay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlipayXmlProcess {

	public AlipayXmlProcess() {

	}

	public static AlipayXmlProcess getInstance() {
		return new AlipayXmlProcess();
	}

	/**
	 * 用于处理订单取消后的远程xml 好像用不到
	 * 
	 * @throws Exception
	 */
	public boolean tradeCancelXmlProcess(String xmlUrl) throws Exception {
		try {
			URL url = new URL(xmlUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer document = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				document.append(line);
			}
			String text = document.toString();
			int index1 = text.indexOf("<is_success>T</is_success>");
			if (index1 != -1) {
				System.out.println("订单取消成功");
				return true;
			} else {
				int index2 = text.indexOf("<error>");
				int index3 = text.indexOf("</error>");
				String reason = text.substring(index2 + 7, index3);
				System.out.println(reason);
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 用于处理订单取消后返回的xml
	 * @return 
	 * 
	 * @throws Exception
	 */
	public boolean tradeCancelStringProcess(String text) throws Exception {
		int index1 = text.indexOf("<is_success>T</is_success>");
		if (index1 != -1) {
			//System.out.println("订单取消成功");
			return true;
		} else {
			//int index2 = text.indexOf("<error>");
			//int index3 = text.indexOf("</error>");
			//String reason = text.substring(index2 + 7, index3);
			//System.out.println(reason);
			return false;
		}

	}
}
