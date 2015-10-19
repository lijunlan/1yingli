package cn.yiyingli.Util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

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

import cn.yiyingli.Data.GlobalData;

@SuppressWarnings("deprecation")
public class GetKeyUtil {

	public static void main(String[] args) {
		getAT();
		getJT();
	}

	public static void refresh() {
		getAT();
		getJT();
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	private static void getJT() {
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

			HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ GlobalData.ACCESS_TOKEN + "&type=jsapi");
					// HttpParams params = httpclient.getParams();

			// params.setParameter("grant_type", "client_credential");
			// params.setParameter("appid", "wxd042cdef58e2e669");
			// params.setParameter("secret",
			// "ff966d65e05fbdcc80bce43c7d8c2896");

			// httpget.setParams(params);
			System.out.println("REQUEST:" + httpget.getURI());
			ResponseHandler responseHandler = new BasicResponseHandler();
			String responseBody;

			responseBody = httpclient.execute(httpget, responseHandler);

			System.out.println(responseBody);
			Map<String, String> data = Json.getMap(responseBody);
			// Create a response handler
			GlobalData.JSAPI_TICKET = data.get("ticket");

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	private static void getAT() {
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
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd042cdef58e2e669&secret=ff966d65e05fbdcc80bce43c7d8c2896");
					// HttpParams params = httpclient.getParams();

			// params.setParameter("grant_type", "client_credential");
			// params.setParameter("appid", "wxd042cdef58e2e669");
			// params.setParameter("secret",
			// "ff966d65e05fbdcc80bce43c7d8c2896");

			// httpget.setParams(params);
			System.out.println("REQUEST:" + httpget.getURI());
			ResponseHandler responseHandler = new BasicResponseHandler();
			String responseBody;

			responseBody = httpclient.execute(httpget, responseHandler);

			System.out.println(responseBody);
			Map<String, String> data = Json.getMap(responseBody);
			// Create a response handler
			GlobalData.ACCESS_TOKEN = data.get("access_token");

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

}
