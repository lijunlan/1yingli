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

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.ExchangeData.ExPassageForBaidu;
import cn.yiyingli.ExchangeData.ExServiceProForBaidu;
import cn.yiyingli.ExchangeData.ExTeacherForBaidu;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SendMsgToBaiduUtil {

	public static void main(String[] args) {
		for (int i = 1; i <= 360; i++) {
			String r = sendPost("http://service.1yingli.cn/yiyingliService/manage",
					"{\"serviceProId\":\"" + i + "\",\"style\":\"function\",\"method\":\"getServicePro\"}");
			JSONObject rjson = JSONObject.fromObject(r);
			if (rjson.getString("state").equals("success")) {
				JSONObject toBaidu = new JSONObject();
				toBaidu.put("Version", 1.0);
				toBaidu.put("ItemId", i + "");
				toBaidu.put("DisplaySwitch", "On");
				toBaidu.put("Url", "http://www.1yingli.cn/service/" +i);
				JSONObject indexed = new JSONObject();
				indexed.put("Title", servicePro.getTitle());
				indexed.put("Content", StringUtil.subStringHTML(servicePro.getContent(), 0xffffff, ""));
				JSONArray labels = new JSONArray();
				JSONObject l = new JSONObject();
				// l.put("Label", servicePro.getKind() + "");
				// l.put("Weight", 5);
				labels.add(l);

				indexed.put("Labels", labels);
				toBaidu.put("Indexed", indexed);

				JSONObject properties = new JSONObject();
				properties.put("Quality", 1);
				JSONArray category = new JSONArray();
				category.add(servicePro.getKind());
				properties.put("Category", category);
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				properties.put("CreateTime", formatter.format(new Date()));
				toBaidu.put("Properties", properties);
				toBaidu.put("Auxiliary", "");
				JSONArray jarray = new JSONArray();
				if (toBaidu != null) {
					jarray.add(toBaidu);
					sendPost("http://ds.recsys.baidu.com/s/142407/276908?token=a4c5f22d60e79cf2779e4d4cff18e5e3",
							jarray.toString());
				}
			}
		}
	}

	public static String updataUserClickData(String json) {
		return sendPost("http://ds.recsys.baidu.com/s/130426/253052?token=8d116fa25cfde0085776beee152741e2", json);
	}

	public static String updataUserClickData(String json, String url) {
		return sendPost(url, json);
	}

	public static String getRecommendPassageListAbout(String pid) {
		return sendGet("http://api.recsys.baidu.com/s/136349/332496/rs?token=68cff3a47d0eeedf083c16d5aabe1628"
				+ "&num=3&iid=" + pid);
	}

	public static String getRecommendServiceProListAbout(String sid) {
		return sendGet("http://api.recsys.baidu.com/s/142407/338550/rs?token=a4c5f22d60e79cf2779e4d4cff18e5e3"
				+ "&num=5&iid=" + sid);
	}

	public static String getRecommendListAbout(String tid) {
		return sendGet(ConfigurationXmlUtil.getInstance().getSettingData().get("baiduAboutUrl") + "&num=5&iid=" + tid);
	}

	public static String getRecommendListIndividuation(String uid) {
		return sendGet(
				ConfigurationXmlUtil.getInstance().getSettingData().get("baiduPersonalUrl") + "&num=5&uid=" + uid);
	}

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static String sendMsgToBaiduTrainData(String json) {
		return sendPost("http://ds.recsys.baidu.com/s/130426/253052?token=8d116fa25cfde0085776beee152741e2", json);
	}

	private static String sendMsgToBaiduTrainData(String json, String url) {
		return sendPost(url, json);
	}

	@SuppressWarnings("unused")
	private static String sendMsgToBaidu(String json) {
		return sendPost("http://ds.recsys.baidu.com/s/130426/253053?token=8d116fa25cfde0085776beee152741e2", json);
	}

	public static void updateUserTrainDataLike(String uid, String tid, String time) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, tid, "rate", (short) 3, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString());
	}

	public static void updateUserTrainDataOrder(String uid, String tid, String time) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, tid, "rate", (short) 5, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString());
	}

	// public static void updateUserTrainDataOrder(String[] uids, String[] tids,
	// String time) {
	// JSONArray sendList = new JSONArray();
	// for (int i = 0; i < (uids.length > tids.length ? tids.length :
	// uids.length); i++) {
	// sendList.add(
	// JSONUserTrainData(uids[i], tids[i], "rate", (short) 5, time.equals("") ?
	// 0L : Long.valueOf(time)));
	// }
	// sendMsgToBaiduTrainData(sendList.toString());
	// }

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

	public static void updateServiceProData(ServicePro servicePro) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray jarray = new JSONArray();
		JSONObject obj = ExServiceProForBaidu.assembleServicePro(servicePro);
		if (obj != null) {
			jarray.add(obj);
			sendPost("http://ds.recsys.baidu.com/s/142407/276908?token=a4c5f22d60e79cf2779e4d4cff18e5e3",
					jarray.toString());
		}
	}

	public static void updateServiceProUserTrainDataLike(String uid, String sid, String time) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, sid, "rate", (short) 5, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString(),
				"http://ds.recsys.baidu.com/s/142407/276910?token=a4c5f22d60e79cf2779e4d4cff18e5e3");
	}

	public static void updateServiceProUserTrainDataRead(String uid, String sid, String time) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, sid, "rate", (short) 1, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString(),
				"http://ds.recsys.baidu.com/s/142407/276910?token=a4c5f22d60e79cf2779e4d4cff18e5e3");
	}

	public static void updatePassageData(Passage passage) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		if (passage.getState() != PassageDao.PASSAGE_STATE_OK)
			return;
		JSONArray jarray = new JSONArray();
		JSONObject obj = ExPassageForBaidu.assemblePassage(passage);
		if (obj != null) {
			jarray.add(obj);
			sendPost("http://ds.recsys.baidu.com/s/136349/264828?token=68cff3a47d0eeedf083c16d5aabe1628",
					jarray.toString());
		}
	}

	public static void updatePassageUserTrainDataLike(String uid, String pid, String time) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, pid, "rate", (short) 5, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString(),
				"http://ds.recsys.baidu.com/s/136349/264829?token=68cff3a47d0eeedf083c16d5aabe1628");
	}

	public static void updatePassageUserTrainDataRead(String uid, String pid, String time) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray sendList = new JSONArray();
		sendList.add(JSONUserTrainData(uid, pid, "rate", (short) 1, time.equals("") ? 0L : Long.valueOf(time)));
		sendMsgToBaiduTrainData(sendList.toString(),
				"http://ds.recsys.baidu.com/s/136349/264829?token=68cff3a47d0eeedf083c16d5aabe1628");
	}

	public static void updateTeacherData(Teacher teacher) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		JSONArray jarray = new JSONArray();
		JSONObject obj = ExTeacherForBaidu.assembleTeacher(teacher);
		if (obj != null) {
			jarray.add(obj);
			sendPost("http://ds.recsys.baidu.com/s/130426/253211?token=8d116fa25cfde0085776beee152741e2",
					jarray.toString());
		}
	}

	private static String sendGet(String url) {
		LogUtil.info("send---->>>" + url, SendMsgToBaiduUtil.class);
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
		LogUtil.info("receive->>>" + result, SendMsgToBaiduUtil.class);
		return result;
	}

	private static String sendPost(String url, String json) {
		LogUtil.info("send---->>>" + json, SendMsgToBaiduUtil.class);
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
		LogUtil.info("receive->>>" + result, SendMsgToBaiduUtil.class);
		return result;
	}
}
