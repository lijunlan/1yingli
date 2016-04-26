package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.yiyingli.ExchangeData.SuperMap;

public class SendMessageUtil {

	public static final short MESSAGE_KIND_VERIFY = 0;

	public static final short MESSAGE_KIND_NOTIFY = 1;

	public static final short MESSAGE_KIND_MARKETING = 2;

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

	public void addSend(String phone, String msg, short kind) {
		SuperMap map = new SuperMap();
		map.put("phone", phone);
		map.put("msg", msg);
		map.put("kind", kind);
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
						String phone = map.finish().getString("phone");
						String msg = map.finish().getString("msg");
						short kind = Short.valueOf(map.finish().getString("kind"));
						LogUtil.info("send message to phone:" + phone + ";message is:" + msg, SendMessageUtil.class);
						if (CheckUtil.checkMobileNumber(phone)) {
							sendChinaMsg(phone, msg);
						} else {
							sendGlobleMsg(phone, msg);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendThread.start();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String message = "这是一条测试短信！验证码12312";
		String phone = "18964959647";
		sendChinaMsg(phone,message);
//		message = URLEncoder.encode(message, "UTF-8");
//		HttpClient httpClient = HttpClients.createDefault();
//		//&from=PaaSoo
//		String url = "http://api.paasoo.com/json?key=rtolso&secret=31MZ0kkx&from=1yingli&to=8618964959647&text=" + message;
//		LogUtil.info("send>>>" + url, SendMailUtil.class);
//		HttpGet get = new HttpGet(url);
//		String result = null;
//		try {
//			HttpResponse response = httpClient.execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				result = EntityUtils.toString(response.getEntity(), "utf-8");
//			}
//			LogUtil.info("receive>>>" + result, SendMailUtil.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static final String MESSAGE_NAME_VERIFY = "yyl-ipxmt";

	private static final String MESSAGE_PASSWD_VERIFY = "IQ8R1Wpy";

	private static final String MESSAGE_NAME_NOTIFY = "yylipxmt2";

	private static final String MESSAGE_PASSWD_NOTIFY = "LE0TMxXd";

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
		SendMessageUtil.getInstance().addSend(phone.replaceAll("-", ""), "尊敬的用户，您的短信验证码为 " + checkNo + "(3分钟内有效)",
				MESSAGE_KIND_VERIFY);
	}

	public static void sendMessage(String phone, String msg) {
		SendMessageUtil.getInstance().addSend(phone.replaceAll("-", ""), msg, MESSAGE_KIND_NOTIFY);
	}

	/**
	 * @param phone e.g. 8615659831720,12176076581
	 * @param msg
	 * @return
	 */
	private static String sendGlobleMsg(String phone, String msg) {
		String message = "";
		try {
			message = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HttpClient httpClient = HttpClients.createDefault();
		String url = "http://api.paasoo.com/json?key=rtolso&secret=31MZ0kkx&from=1yingli&to=" + phone + "&text=" + message;
//		switch (kind) {
//		case MESSAGE_KIND_VERIFY:
//			url = "http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME_VERIFY + "&cppwd="
//					+ MESSAGE_PASSWD_VERIFY + "&da=" + phone + "&dc=15&sm=" + message;
//			break;
//		case MESSAGE_KIND_NOTIFY:
//			url = "http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME_NOTIFY + "&cppwd="
//					+ MESSAGE_PASSWD_NOTIFY + "&da=" + phone + "&dc=15&sm=" + message;
//			break;
//		default:
//			url = "http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME_NOTIFY + "&cppwd="
//					+ MESSAGE_PASSWD_NOTIFY + "&da=" + phone + "&dc=15&sm=" + message;
//			break;
//		}
		HttpGet get = new HttpGet(url);
		LogUtil.info("send>>>" + url, SendMailUtil.class);
		String result = null;
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
			LogUtil.info("receive>>>" + result, SendMailUtil.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param phone e.g. 15659831720
	 * @param msg
	 * @return
	 */
	private static String sendChinaMsg(String phone, String msg) {
		String message = "";
		try {
			message = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HttpClient httpClient = HttpClients.createDefault();
		String url = "http://api.paasoo.com/json?key=rtolso&secret=31MZ0kkx&from=1yingli&to=86" + phone + "&text=" + message;
//		switch (kind) {
//			case MESSAGE_KIND_VERIFY:
//				url = "http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME_VERIFY + "&cppwd="
//						+ MESSAGE_PASSWD_VERIFY + "&da=86" + phone + "&dc=15&sm=" + message;
//				break;
//			case MESSAGE_KIND_NOTIFY:
//				url = "http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME_NOTIFY + "&cppwd="
//						+ MESSAGE_PASSWD_NOTIFY + "&da=86" + phone + "&dc=15&sm=" + message;
//				break;
//			default:
//				url = "http://api2.santo.cc/submit?command=MT_REQUEST&cpid=" + MESSAGE_NAME_NOTIFY + "&cppwd="
//						+ MESSAGE_PASSWD_NOTIFY + "&da=86" + phone + "&dc=15&sm=" + message;
//				break;
//		}
		HttpGet get = new HttpGet(url);
		LogUtil.info("send>>>" + url, SendMailUtil.class);
		String result = null;
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
			LogUtil.info("receive>>>" + result, SendMailUtil.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
