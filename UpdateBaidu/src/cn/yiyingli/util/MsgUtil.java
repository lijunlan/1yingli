package cn.yiyingli.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MsgUtil {

	public static String sendMsgToBaidu(String json) {
		return send("http://ds.recsys.baidu.com/s/130426/253211?token=8d116fa25cfde0085776beee152741e2", json);
	}

	public static String sendMsgToBaidu(String json, String url) {
		return send(url, json);
	}

	private static String send(String url, String json) {
		System.out.println("send>>>" + json);
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader(HTTP.CONTENT_TYPE, "text/json");
		StringEntity se = new StringEntity(json, "utf-8");
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "utf-8"));
		post.setEntity(se);
		String result = "";
		try {
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("receive>>" + result);
		return result;
	}

	public static void main(String[] args) {
		send("http://test.1yingli.cn/yiyingliService/manage", "{}");
	}

	public static String sendMsgToBaiduTrainData(String json) {
		return send("http://ds.recsys.baidu.com/s/130426/253052?token=8d116fa25cfde0085776beee152741e2", json);
	}

	public static String sendMsgToService(String json) {
		return send("http://service.1yingli.cn/yiyingliService/manage", json);
	}
}
