package cn.yiyingli.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SendMsgToBaiduUtil {
	
	public static void main(String[] args){
		System.out.println(sendGet("http://api2.santo.cc/submit?command=USER_BALANCE&uid=yyl-ipxmt&pwd=IQ8R1Wpy"));
	}

	public static String updataUserData(String json) {
		return sendPost("http://ds.recsys.baidu.com/s/130426/253052?token=8d116fa25cfde0085776beee152741e2", json);
	}

	public static String getRecommendListAbout(String tid) {
		return sendGet(
				"http://api.recsys.baidu.com/s/130426/326497/rs?token=8d116fa25cfde0085776beee152741e2&num=5&iid="
						+ tid);
	}

	public static String getRecommendListIndividuation(String uid) {
		return sendGet(
				"http://api.recsys.baidu.com/s/130426/326498/rs?token=8d116fa25cfde0085776beee152741e2&num=5&uid="
						+ uid);
	}

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static String sendMsgToBaiduTrainData(String json) {
		return sendPost("http://ds.recsys.baidu.com/s/130426/253052?token=8d116fa25cfde0085776beee152741e2", json);
	}

	@SuppressWarnings("unused")
	private static String sendMsgToBaidu(String json) {
		return sendPost("http://ds.recsys.baidu.com/s/130426/253053?token=8d116fa25cfde0085776beee152741e2", json);
	}

	public static void updateUserTrainDataLike(String uid, String tid, String time) {
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, tid, "rate", (short) 3, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString());
	}

	public static void updateUserTrainDataOrder(String uid, String tid, String time) {
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, tid, "rate", (short) 5, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString());
	}

	public static JSONObject JSONUserTrainData(String uid, String tid, String action, short rate, long time) {
		JSONObject toBaidu = new JSONObject();
		toBaidu.put("UserId", uid);
		toBaidu.put("ItemId", tid);
		toBaidu.put("Action", action);
		if (action.equals("rate")) {
			JSONObject jsonContext = new JSONObject();
			jsonContext.put("Score", rate);
			toBaidu.put("Context", jsonContext);
		} else {
			toBaidu.put("Context", new JSONObject());
		}
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		toBaidu.put("Timestamp", formatter.format(new Date(time)));
		return toBaidu;
	}

	private static String sendGet(String url) {
		System.out.println("send>>>" + url);
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		String result = "";
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("receive>>" + result);
		return result;
	}

	private static String sendPost(String url, String json) {
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
}
