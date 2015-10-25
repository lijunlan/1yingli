package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.yiyingli.ExchangeData.SuperMap;

public class SendMessageUtil {

	private static SendMessageUtil singleInstance;

	public static SendMessageUtil getInstance() {
		if (singleInstance == null) {
			singleInstance = new SendMessageUtil();
			singleInstance.start();
		}
		return singleInstance;
	}

	private LinkedBlockingQueue<SuperMap> sendQueue;

	public SendMessageUtil() {
		sendQueue = new LinkedBlockingQueue<SuperMap>();
	}

	public void addSend(String phone, String msg) {
		SuperMap map = new SuperMap();
		map.put("phone", phone);
		map.put("msg", msg);
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
						sendMsg(map.finish().get("phone"), map.finish().get("msg"));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendThread.start();
	}

	public static void main(String[] args) {
		// for (int i = 1; i <= 1; i++) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// for (int i = 1; i <= 1; i++) {
		// sendMsg("15659831720", "CHECKNO:<a
		// href=\"http://www.1yingli.cn\">test</a>" +
		// CheckNoFactory.createCheckNo());
		// }
		// }
		// }).start();
		// }
		// System.out.println(sendMessage("8615659831720",
		// "马总好，我是道哥，测试一用，收到请微信，谢谢~"));
		// System.out.println(sendMessage("12176076581",
		// "马总好，我是道哥，测试一用，收到请微信，谢谢~"));
		// String regEx="[^0-9]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher("+86-15659831720");
		// System.out.println(m.replaceAll("").trim());
	}

	private static final String MESSAGE_NAME = "yyl-ipxmt";

	private static final String MESSAGE_PASSWD = "IQ8R1Wpy";

	private static String hexString(byte[] b) {
		StringBuffer d = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
			char lo = Character.forDigit(b[i] & 0x0F, 16);
			d.append(Character.toUpperCase(hi));
			d.append(Character.toUpperCase(lo));
		}
		return d.toString().toLowerCase();
	}

	public static void sendCheckNo(String phone, String checkNo) {
		SendMessageUtil.getInstance().addSend(phone, "尊敬的用户，您的短信验证码为 " + checkNo + "(3分钟内有效)");
	}

	public static void sendMessage(String phone, String msg) {
		SendMessageUtil.getInstance().addSend(phone, msg);
	}

	/**
	 * @param phone
	 *            e.g. 8615659831720,12176076581
	 * @param msg
	 * @return
	 */
	private static String sendMsg(String phone, String msg) {
		String message = "";
		try {
			message = hexString(msg.getBytes("GBK"));

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME + "&cppwd="
				+ MESSAGE_PASSWD + "&da=86" + phone + "&dc=15&sm=" + message);
		String result = null;
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
