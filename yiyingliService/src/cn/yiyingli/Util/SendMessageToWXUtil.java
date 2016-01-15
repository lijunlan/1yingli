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

	private static final String APPID = "wx526dfd5c9193f98c";

	private static final String MCHID = "1307236401";

	private static final String NOTIFY_URL = "http://service.1yingli.cn/yiyingliService/WXNotify";

	private static final String NOTIFY_URL_DEBUG = "http://test21.1yingli.cn/yiyingliService/WXNotify";

	public static String unifyOrder() {
		return "";
	}

	public static String getRandomStr() {
		StringBuilder originStr = new StringBuilder();
		for (int i = 1; i <= 12; i++) {
			originStr.append(new Random().nextInt(10));
		}
		return MD5Util.MD5(originStr.toString());
	}
	
	public static String getSign(JSONObject json,String nonce_str){
		StringBuilder originStr = new StringBuilder();
		originStr.append("appid=");
		originStr.append(APPID);
		originStr.append("&attach=");
		originStr.append("");
		originStr.append("&body=");
		originStr.append("");
		originStr.append("&mch_id=");
		originStr.append("");
		originStr.append("&nonce_str=");
		originStr.append("");
		originStr.append("&notify_url=");
		originStr.append("");
		originStr.append("&out_trade_no=");
		originStr.append("");
		originStr.append("&spbill_create_ip=");
		originStr.append("");
		originStr.append("&total_fee=");
		originStr.append("");
		originStr.append("&trade_type=");
		originStr.append("");
		originStr.append("&key=");
		return "";
		
	}

	public static String json2Xml(JSONObject json) {
		String nonce_str = getRandomStr();
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<xml><appid>");
		xmlBuilder.append(APPID);
		xmlBuilder.append("</appid>");
		xmlBuilder.append("<attach>");
		xmlBuilder.append("");
		xmlBuilder.append("</attach>");
		xmlBuilder.append("<body>");
		xmlBuilder.append("");
		xmlBuilder.append("</body>");
		xmlBuilder.append("<mch_id>");
		xmlBuilder.append(MCHID);
		xmlBuilder.append("</mch_id>");
		xmlBuilder.append("<nonce_str>");
		xmlBuilder.append(nonce_str);
		xmlBuilder.append("</nonce_str>");
		xmlBuilder.append("<notify_url>");
		xmlBuilder.append(NOTIFY_URL);
		xmlBuilder.append("</notify_url>");
		xmlBuilder.append("<out_trade_no>");
		xmlBuilder.append("");
		xmlBuilder.append("</out_trade_no>");  
		xmlBuilder.append("<spbill_create_ip>");
		xmlBuilder.append("");
		xmlBuilder.append("</spbill_create_ip>");  
		xmlBuilder.append("<total_fee>");
		xmlBuilder.append("");
		xmlBuilder.append("</total_fee>");  
		xmlBuilder.append("<trade_type>");
		xmlBuilder.append("NATIVE");
		xmlBuilder.append("</trade_type>");    
		   //<openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
		
//		   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
//		</xml>
		return "";
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
