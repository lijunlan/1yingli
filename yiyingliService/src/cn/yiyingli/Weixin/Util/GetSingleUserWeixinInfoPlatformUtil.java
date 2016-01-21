package cn.yiyingli.Weixin.Util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class GetSingleUserWeixinInfoPlatformUtil {

	public static final String APPID_WEB_PLATFORM = "wx526dfd5c9193f98c";
	public static final String SECRET_WEB_PLATFORM = "f8f53de1babacb830f084e0fe22c1a4c";

	public static void main(String[] args) {
		JSONObject accessToken = getAccessToken("001de48c660abfcd47ccc5f672ea8bbp");
		if (accessToken == null || accessToken.containsKey("errcode")) {
			return;
		}
		System.out.println(accessToken);
		JSONObject userInfo = getUserInfo((String) accessToken.get("openid"), (String) accessToken.get("access_token"));
		if (userInfo == null || userInfo.containsKey("errcode")) {
			return;
		}
		System.out.println(userInfo);
		String id = (String) userInfo.get("openid");
		String nickname = (String) userInfo.get("nickname");
		String p = (String) userInfo.get("province");
		String ci = (String) userInfo.get("city");
		String co = (String) userInfo.get("country");
		String icon = (String) userInfo.get("headimgurl");
		System.out.println(id + "\n" + nickname + "\n" + p + ci + co + "\n" + icon);
	}

	@SuppressWarnings({ "resource" })
	public static JSONObject getUserInfo(String openid, String access_token) {
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

			HttpGet httpget = new HttpGet("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
					+ "&openid=" + openid + "&lang=zh_CN");

			HttpResponse response = httpclient.execute(httpget);
			String result = "{}";
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
			JSONObject data = JSONObject.fromObject(result);
			return data;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static JSONObject getAccessToken(String code) {
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

			String urls = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID_WEB_PLATFORM + "&secret="
					+ SECRET_WEB_PLATFORM + "&code=" + code + "&grant_type=authorization_code";
			HttpGet httpget = new HttpGet(urls);

			ResponseHandler responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpget, responseHandler);
			JSONObject data = JSONObject.fromObject(responseBody);
			return data;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}
}
