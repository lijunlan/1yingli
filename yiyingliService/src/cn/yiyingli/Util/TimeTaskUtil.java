package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.yiyingli.ExchangeData.SuperMap;

public class TimeTaskUtil {

	private static TimeTaskUtil singleInstance;

	public static TimeTaskUtil getInstance() {
		if (singleInstance == null) {
			singleInstance = new TimeTaskUtil();
			singleInstance.start();
		}
		return singleInstance;
	}

	private LinkedBlockingQueue<SuperMap> sendQueue;

	public TimeTaskUtil() {
		sendQueue = new LinkedBlockingQueue<SuperMap>();
	}

	public void addSend(SuperMap map) {
		try {
			sendQueue.put(map);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		Thread sendThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						SuperMap map = sendQueue.take();
						boolean result = false;
						try {
							result = sendRequest(map);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						if (!result) {
							sendQueue.add(map);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendThread.start();
	}

	private boolean sendRequest(SuperMap map) throws UnsupportedEncodingException {
		String json = map.finishByJson();
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://timetask.1yingli.cn/TimeTasker/taskReceive");

		LogUtil.info(json, this.getClass());
		StringEntity se = new StringEntity(json);
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "utf-8"));
		post.setEntity(se);
		String result = null;
		try {
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
			LogUtil.info(result, this.getClass());
			if (result.contains("success"))
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void sendTimeTask(String action, String kind, String endTime, String data) {
		SuperMap map = new SuperMap();
		map.put("action", action);
		map.put("kind", kind);
		map.put("endTime", endTime);
		map.put("data", data);
		TimeTaskUtil timeUtil = getInstance();
		timeUtil.addSend(map);
	}

	public static void main(String[] args) {
		sendTimeTask("change", "order", (Calendar.getInstance().getTimeInMillis() + 1000 * 60) + "",
				"\"{'state':'0100','orderId':'12312312312'}\"");
	}
}
