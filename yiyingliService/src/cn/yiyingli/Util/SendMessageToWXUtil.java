package cn.yiyingli.Util;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class SendMessageToWXUtil {

	private static final String UNIFY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	private static final String APPID = "wx526dfd5c9193f98c";

	private static final String MCHID = "1307236401";

	private static final String KEY = "6C448DCD81D942A15EE79BD3221E78D4";

	private static final String NOTIFY_URL = "http://service.1yingli.cn/yiyingliService/WXNotify";

	private static final String NOTIFY_URL_DEBUG = "http://test21.1yingli.cn/yiyingliService/WXNotify";

	public static String unifyOrder(JSONObject orderInfo) {
		String sendMsg = json2Xml(orderInfo);
		String result = sendData(UNIFY_ORDER_URL, sendMsg);
		String return_code = getContent(result, "return_code");
		String result_code = getContent(result, "result_code");
		if ("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)) {
			String code_url = getContent(result, "code_url");
			return code_url;
		} else {
			LogUtil.error(result, SendMessageToWXUtil.class);
			return "";
		}
	}

	public static void main(String[] args) {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid></xml>";
		String tt = getContent(xml, "appid");
		System.out.println(tt);
	}

	private static String getContent(String xml, String key) {
		int start = xml.indexOf("<" + key + ">") + 2 + key.length();
		int end = xml.indexOf("</" + key + ">");
		String content = xml.substring(start, end);
		if (content.startsWith("<![CDATA[") && content.endsWith("]]>")) {
			content = content.substring(9, content.length() - 3);
		}
		return content;
	}

	public static String getRandomStr() {
		StringBuilder originStr = new StringBuilder();
		for (int i = 1; i <= 12; i++) {
			originStr.append(new Random().nextInt(10));
		}
		return MD5Util.MD5(originStr.toString());
	}

	public static String getSign(JSONObject json, String nonce_str, String notify_url) {
		StringBuilder originStr = new StringBuilder();
		originStr.append("appid=");
		originStr.append(APPID);
		originStr.append("&attach=");
		originStr.append(json.getString("extra_param"));
		originStr.append("&body=");
		originStr.append(json.getString("content"));
		originStr.append("&mch_id=");
		originStr.append(MCHID);
		originStr.append("&nonce_str=");
		originStr.append(nonce_str);
		originStr.append("&notify_url=");
		originStr.append(notify_url);
		originStr.append("&out_trade_no=");
		originStr.append(json.getString("oid"));
		originStr.append("&spbill_create_ip=");
		originStr.append(json.getString("ip"));
		originStr.append("&total_fee=");
		originStr.append(json.getString("money"));
		originStr.append("&trade_type=");
		originStr.append("NATIVE");
		originStr.append("&key=");
		originStr.append(KEY);
		return MD5Util.MD5(originStr.toString());
	}

	public static String json2Xml(JSONObject json) {
		String nonce_str = getRandomStr();
		String notify_url = "false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))
				? NOTIFY_URL : NOTIFY_URL_DEBUG;
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<xml><appid>");
		xmlBuilder.append(APPID);
		xmlBuilder.append("</appid>");
		xmlBuilder.append("<attach>");
		xmlBuilder.append(json.getString("extra_param"));
		xmlBuilder.append("</attach>");
		xmlBuilder.append("<body>");
		xmlBuilder.append(json.getString("content"));
		xmlBuilder.append("</body>");
		xmlBuilder.append("<mch_id>");
		xmlBuilder.append(MCHID);
		xmlBuilder.append("</mch_id>");
		xmlBuilder.append("<nonce_str>");
		xmlBuilder.append(nonce_str);
		xmlBuilder.append("</nonce_str>");
		xmlBuilder.append("<notify_url>");
		xmlBuilder.append(notify_url);
		xmlBuilder.append("</notify_url>");
		xmlBuilder.append("<out_trade_no>");
		xmlBuilder.append(json.getString("oid"));
		xmlBuilder.append("</out_trade_no>");
		xmlBuilder.append("<spbill_create_ip>");
		xmlBuilder.append(json.getString("ip"));
		xmlBuilder.append("</spbill_create_ip>");
		xmlBuilder.append("<total_fee>");
		xmlBuilder.append(json.getString("money"));
		xmlBuilder.append("</total_fee>");
		xmlBuilder.append("<trade_type>");
		xmlBuilder.append("NATIVE");
		xmlBuilder.append("</trade_type>");
		xmlBuilder.append("<sign>");
		xmlBuilder.append(getSign(json, nonce_str, notify_url));
		xmlBuilder.append("</sign>");
		return xmlBuilder.toString();
	}

	@SuppressWarnings({ "resource" })
	public static String sendData(String url, String msg) {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			// Secure Protocol implementation.
			SSLContext ctx = SSLContext.getInstance("SSL");
			// Implementation of a trust manager for X509 certificates
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);

			ClientConnectionManager ccm = httpclient.getConnectionManager();
			// register https protocol in httpclient's scheme registry
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));

			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "text/xml");
			StringEntity se = new StringEntity(msg, "utf-8");
			se.setContentType("text/xml");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "utf-8"));
			httpPost.setEntity(se);
			String result = "";
			try {
				HttpResponse response = httpclient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity(), "utf-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			LogUtil.info("receive->>>" + result, SendMsgToBaiduUtil.class);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return "";
	}
}
