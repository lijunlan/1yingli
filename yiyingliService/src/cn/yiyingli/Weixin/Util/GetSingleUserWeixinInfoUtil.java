package cn.yiyingli.Weixin.Util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class GetSingleUserWeixinInfoUtil {

	public static final String APPID_WEB = "wxe8dcba556150ef5a";
	public static final String APPID_MOBILE = "wx9f8dd4b9cbd736a6";
	public static final String SECRET_WEB = "2cdad51d2422a84b5dd373e49b5ee75e";
	public static final String SECRET_MOBILE = "2a43bd1763ec657e7114df63fd0af290";
	public static final short KIND_MOBILE = 0;
	public static final short KIND_WEB = 1;

	public static void main(String[] args) {
		JSONObject accessToken = getAccessToken("03151d42990f18b6a942683c9da75d0A", KIND_WEB);
		if (accessToken == null || accessToken.containsKey("errcode")) {
			return;
		}
		System.out.println(accessToken);
		JSONObject userInfo = getUserInfo((String) accessToken.get("openid"), (String) accessToken.get("access_token"));
		if (userInfo == null || userInfo.containsKey("errcode")) {
			return;
		}
		System.out.println(userInfo);
		String id = (String) userInfo.get("unionid");
		String nickname = (String) userInfo.get("nickname");
		String p = (String) userInfo.get("province");
		String ci = (String) userInfo.get("city");
		String co = (String) userInfo.get("country");
		String icon = (String) userInfo.get("headimgurl");
		System.out.println(id + "\n" + nickname + "\n" + p + ci + co + "\n" + icon);
	}

	@SuppressWarnings({ "resource", "rawtypes", "unchecked" })
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

			HttpGet httpget = new HttpGet(
					"https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid);

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

	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static JSONObject getAccessToken(String code, short kind) {
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

			String urls = "";
			if (kind == KIND_WEB) {
				urls = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID_WEB + "&secret=" + SECRET_WEB
						+ "&code=" + code + "&grant_type=authorization_code";
			} else if (kind == KIND_MOBILE) {
				urls = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID_MOBILE + "&secret="
						+ SECRET_MOBILE + "&code=" + code + "&grant_type=authorization_code";
			}
			HttpGet httpget = new HttpGet(urls);
			
			// System.out.println("REQUEST:" + httpget.getURI());
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
