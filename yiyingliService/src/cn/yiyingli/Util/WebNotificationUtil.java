package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.yiyingli.ExchangeData.SuperMap;
import net.sf.json.JSONObject;

public class WebNotificationUtil {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static void main(String[] args) throws UnsupportedEncodingException {

		// sendMsg("啪啪啪");
		sendMsgBySingle("001",
				"%7B%27title%27%3A%27%E5%B0%8A%E6%95%AC%E7%9A%84%E5%AF%BC%E5%B8%88%EF%BC%8C%28%E8%AE%A2%E5%8D%95%E5%8F%B7%3A2015032100000212%29%EF%BC%8C%E5%AD%A6%E5%91%98%28%E5%BC%A0%E8%B4%BA%29%E5%B7%B2%E7%BB%8F%E5%AF%B9%E5%92%A8%E8%AF%A2%E8%BF%9B%E8%A1%8C%E4%BA%86%E8%AF%84%E4%BB%B7%28%E8%AF%84%E4%BB%B7%E5%88%86%E6%95%B0%3A5%E5%88%86%E3%80%82%E8%AF%84%E4%BB%B7%E5%86%85%E5%AE%B9%E5%BE%88%E6%9C%89%E8%80%90%E5%BF%83%E7%9A%84%E5%AD%A6%E5%A7%90%EF%BC%8C%E5%9C%A8%E6%AD%A3%E5%BC%8F%E8%81%8A%E4%B9%8B%E5%89%8D%EF%BC%8C%E5%AD%A6%E5%A7%90%E5%BE%88%E7%BB%86%E5%BF%83%E7%9A%84%E5%92%8C%E6%88%91%E8%BF%9B%E8%A1%8C%E4%BA%86%E6%B2%9F%E9%80%9A%EF%BC%8C%E5%8C%85%E6%8B%AC%E6%88%91%E6%83%B3%E5%92%A8%E8%AF%A2%E5%93%AA%E4%BA%9B%E6%96%B9%E9%9D%A2%E4%BB%A5%E5%8F%8A%E7%8E%B0%E9%98%B6%E6%AE%B5%E7%9A%84%E4%B8%80%E4%BA%9B%E5%9B%B0%E6%83%91%E7%AD%89%E7%AD%89%EF%BC%8C%E6%9C%89%E4%BA%86%E5%BE%88%E5%A5%BD%E7%9A%84%E5%89%8D%E6%9C%9F%E5%87%86%E5%A4%87%EF%BC%8C%E6%89%80%E4%BB%A5%E6%B2%9F%E9%80%9A%E7%9A%84%E5%BE%88%E9%A1%BA%E7%95%85%E3%80%82%E6%84%9F%E8%B0%A2%E5%AD%A6%E5%A7%90%E7%BB%99%E6%88%91%E7%9A%84%E4%B8%93%E4%B8%9A%E6%8C%87%E5%AF%BC%EF%BC%8C%E4%B8%80%E5%AE%9A%E4%BC%9A%E7%BB%A7%E7%BB%AD%E5%8A%AA%E5%8A%9B%E7%9A%84%EF%BC%81%29%28http%3A%2F%2Fwww.1yingli.cn%2Ftutor.html%29%27%2C%27url%27%3A%27%23%27%7D\"}%E8%AF%84%E4%BB%B7%28%E8%AF%84%E4%BB%B7%E5%88%86%E6%95%B0%3A5%E5%88%86%E3%80%82%E8%AF%84%E4%BB%B7%E5%86%85%E5%AE%B9%E5%BE%88%E6%9C%89%E8%80%90%E5%BF%83%E7%9A%84%E5%AD%A6%E5%A7%90%EF%BC%8C%E5%9C%A8%E6%AD%A3%E5%BC%8F%E8%81%8A%E4%B9%8B%E5%89%8D%EF%BC%8C%E5%AD%A6%E5%A7%90%E5%BE%88%E7%BB%86%E5%BF%83%E7%9A%84%E5%92%8C%E6%88%91%E8%BF%9B%E8%A1%8C%E4%BA%86%E6%B2%9F%E9%80%9A%EF%BC%8C%E5%8C%85%E6%8B%AC%E6%88%91%E6%83%B3%E5%92%A8%E8%AF%A2%E5%93%AA%E4%BA%9B%E6%96%B9%E9%9D%A2%E4%BB%A5%E5%8F%8A%E7%8E%B0%E9%98%B6%E6%AE%B5%E7%9A%84%E4%B8%80%E4%BA%9B%E5%9B%B0%E6%83%91%E7%AD%89%E7%AD%89%EF%BC%8C%E6%9C%89%E4%BA%86%E5%BE%88%E5%A5%BD%E7%9A%84%E5%89%8D%E6%9C%9F%E5%87%86%E5%A4%87%EF%BC%8C%E6%89%80%E4%BB%A5%E6%B2%9F%E9%dsvffdsgdsffdsfwesfwefwefe");
		// SuperMap map = new SuperMap();
		// map.put("uid", "2e638a98-83dd-4130-875d-92369b9e4ea6");
		// map.put("data",
		// "%7B%27title%27%3A%27%E5%B0%8A%E6%95%AC%E7%9A%84%E5%AF%BC%E5%B8%88%EF%BC%8C%28%E8%AE%A2%E5%8D%95%E5%8F%B7%3A2015032100000212%29%EF%BC%8C%E5%AD%A6%E5%91%98%28%E5%BC%A0%E8%B4%BA%29%E5%B7%B2%E7%BB%8F%E5%AF%B9%E5%92%A8%E8%AF%A2%E8%BF%9B%E8%A1%8C%E4%BA%86%E8%AF%84%E4%BB%B7%28%E8%AF%84%E4%BB%B7%E5%88%86%E6%95%B0%3A5%E5%88%86%E3%80%82%E8%AF%84%E4%BB%B7%E5%86%85%E5%AE%B9%E5%BE%88%E6%9C%89%E8%80%90%E5%BF%83%E7%9A%84%E5%AD%A6%E5%A7%90%EF%BC%8C%E5%9C%A8%E6%AD%A3%E5%BC%8F%E8%81%8A%E4%B9%8B%E5%89%8D%EF%BC%8C%E5%AD%A6%E5%A7%90%E5%BE%88%E7%BB%86%E5%BF%83%E7%9A%84%E5%92%8C%E6%88%91%E8%BF%9B%E8%A1%8C%E4%BA%86%E6%B2%9F%E9%80%9A%EF%BC%8C%E5%8C%85%E6%8B%AC%E6%88%91%E6%83%B3%E5%92%A8%E8%AF%A2%E5%93%AA%E4%BA%9B%E6%96%B9%E9%9D%A2%E4%BB%A5%E5%8F%8A%E7%8E%B0%E9%98%B6%E6%AE%B5%E7%9A%84%E4%B8%80%E4%BA%9B%E5%9B%B0%E6%83%91%E7%AD%89%E7%AD%89%EF%BC%8C%E6%9C%89%E4%BA%86%E5%BE%88%E5%A5%BD%E7%9A%84%E5%89%8D%E6%9C%9F%E5%87%86%E5%A4%87%EF%BC%8C%E6%89%80%E4%BB%A5%E6%B2%9F%E9%80%9A%E7%9A%84%E5%BE%88%E9%A1%BA%E7%95%85%E3%80%82%E6%84%9F%E8%B0%A2%E5%AD%A6%E5%A7%90%E7%BB%99%E6%88%91%E7%9A%84%E4%B8%93%E4%B8%9A%E6%8C%87%E5%AF%BC%EF%BC%8C%E4%B8%80%E5%AE%9A%E4%BC%9A%E7%BB%A7%E7%BB%AD%E5%8A%AA%E5%8A%9B%E7%9A%84%EF%BC%81%29%28http%3A%2F%2Fwww.1yingli.cn%2Ftutor.html%29%27%2C%27url%27%3A%27%23%27%7D\"}%E8%AF%84%E4%BB%B7%28%E8%AF%84%E4%BB%B7%E5%88%86%E6%95%B0%3A5%E5%88%86%E3%80%82%E8%AF%84%E4%BB%B7%E5%86%85%E5%AE%B9%E5%BE%88%E6%9C%89%E8%80%90%E5%BF%83%E7%9A%84%E5%AD%A6%E5%A7%90%EF%BC%8C%E5%9C%A8%E6%AD%A3%E5%BC%8F%E8%81%8A%E4%B9%8B%E5%89%8D%EF%BC%8C%E5%AD%A6%E5%A7%90%E5%BE%88%E7%BB%86%E5%BF%83%E7%9A%84%E5%92%8C%E6%88%91%E8%BF%9B%E8%A1%8C%E4%BA%86%E6%B2%9F%E9%80%9A%EF%BC%8C%E5%8C%85%E6%8B%AC%E6%88%91%E6%83%B3%E5%92%A8%E8%AF%A2%E5%93%AA%E4%BA%9B%E6%96%B9%E9%9D%A2%E4%BB%A5%E5%8F%8A%E7%8E%B0%E9%98%B6%E6%AE%B5%E7%9A%84%E4%B8%80%E4%BA%9B%E5%9B%B0%E6%83%91%E7%AD%89%E7%AD%89%EF%BC%8C%E6%9C%89%E4%BA%86%E5%BE%88%E5%A5%BD%E7%9A%84%E5%89%8D%E6%9C%9F%E5%87%86%E5%A4%87%EF%BC%8C%E6%89%80%E4%BB%A5%E6%B2%9F%E9%dsvffdsgdsffdsfwesfwefwefe");
		// map.put("castType", "UNI");
		// System.out.println((map.finishByJson()));
	}

	/**
	 * 单播
	 * 
	 * @param uid
	 * @param msg
	 * @throws UnsupportedEncodingException
	 */
	public static void sendMsgBySingle(String uid, String msg) throws UnsupportedEncodingException {
		SuperMap map = new SuperMap();
		map.put("uid", uid);
		map.put("data", URLEncoder.encode(msg, "utf-8"));
		map.put("castType", "UNI");
		send(map.finishByJson());
	}

	/**
	 * 组播
	 * 
	 * @param group
	 * @param msg
	 * @throws UnsupportedEncodingException
	 */
	public static void sendMsgByGroup(String group, String msg) throws UnsupportedEncodingException {
		SuperMap map = new SuperMap();
		map.put("group", group);
		map.put("castType", "MULTI");
		map.put("data", URLEncoder.encode(msg, "utf-8"));
		send(map.finishByJson());
	}

	/**
	 * 广播
	 * 
	 * @param msg
	 * @throws UnsupportedEncodingException
	 */
	public static void sendMsg(String msg) throws UnsupportedEncodingException {
		SuperMap map = new SuperMap();
		map.put("data", URLEncoder.encode(msg, "utf-8"));
		map.put("castType", "BROAD");
		send(map.finishByJson());
	}

	private static boolean send(String json) throws UnsupportedEncodingException {
		String r = sendNotification(json);
		LogUtil.info(r, WebNotificationUtil.class);
		JSONObject map = JSONObject.fromObject(r);
		if ("success".equals(map.getString("state")))
			return true;
		return false;
	}

	private static String sendNotification(String json) throws UnsupportedEncodingException {
		// json = URLEncoder.encode(json, "utf-8");
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://notifi.1yingli.cn/NotificationService/noti");
		post.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

		StringEntity se = new StringEntity(json,"utf-8");
		se.setContentType(CONTENT_TYPE_TEXT_JSON);
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
		post.setEntity(se);
		String result = null;
		try {
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
