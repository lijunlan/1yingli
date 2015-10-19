package cn.yiyingli.BaichuanTaobaoUtil;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.CloudpushMessageAndroidRequest;
import com.taobao.api.request.CloudpushMessageIosRequest;
import com.taobao.api.request.CloudpushNoticeAndroidRequest;
import com.taobao.api.request.CloudpushNoticeIosRequest;
import com.taobao.api.request.CloudpushPushRequest;
import com.taobao.api.response.CloudpushMessageAndroidResponse;
import com.taobao.api.response.CloudpushMessageIosResponse;
import com.taobao.api.response.CloudpushNoticeAndroidResponse;
import com.taobao.api.response.CloudpushNoticeIosResponse;
import com.taobao.api.response.CloudpushPushResponse;

public class CloudPushUtil {

	private static final String appkey = "23226220";
	private static final String secret = "32462bfecf3b9a01d3b89441f5a9eefd";
	private static final String url = "http://gw.api.taobao.com/router/rest";

	private static String array2str(String[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(array[i]);
		}
		return sb.toString();
	}

	public static boolean IOSpushMessageToAll(String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushMessageIosRequest req = new CloudpushMessageIosRequest();
		req.setBody(msg);
		req.setTarget("all");
		req.setTargetValue("all");
		try {
			CloudpushMessageIosResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean IOSpushMessageToAccount(String[] usernames, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushMessageIosRequest req = new CloudpushMessageIosRequest();
		req.setBody(msg);
		req.setTarget("account");
		req.setTargetValue(array2str(usernames));
		try {
			CloudpushMessageIosResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean AndroidpushMessageToAll(String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushMessageAndroidRequest req = new CloudpushMessageAndroidRequest();
		req.setBody(msg);
		req.setTarget("all");
		req.setTargetValue("all");
		try {
			CloudpushMessageAndroidResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean AndroidpushMessageToAccount(String[] usernames, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushMessageAndroidRequest req = new CloudpushMessageAndroidRequest();
		req.setBody(msg);
		req.setTarget("account");
		req.setTargetValue(array2str(usernames));
		try {
			CloudpushMessageAndroidResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean IOSpushNoticeToAll(String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushNoticeIosRequest req = new CloudpushNoticeIosRequest();
		req.setSummary(msg);
		req.setTarget("all");
		req.setTargetValue("all");
		req.setEnv("DEV");
		req.setExt("{'badge':1,'sound':'xxxx'}");
		try {
			CloudpushNoticeIosResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean IOSpushNoticeToAccount(String[] usernames, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushNoticeIosRequest req = new CloudpushNoticeIosRequest();
		req.setSummary(msg);
		req.setTarget("account");
		req.setTargetValue(array2str(usernames));
		req.setEnv("DEV");
		req.setExt("{'badge':1,'sound':'xxxx'}");
		try {
			CloudpushNoticeIosResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean AndroidNotficeToAll(String title, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushNoticeAndroidRequest req = new CloudpushNoticeAndroidRequest();
		req.setSummary(msg);
		req.setTitle(msg);
		req.setTarget("all");
		req.setTargetValue("all");
		try {
			CloudpushNoticeAndroidResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean AndroidNotficeToAccount(String[] usernames, String title, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushNoticeAndroidRequest req = new CloudpushNoticeAndroidRequest();
		req.setSummary(msg);
		req.setTitle(title);
		req.setTarget("account");
		req.setTargetValue(array2str(usernames));
		try {
			CloudpushNoticeAndroidResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean IOSpushToAll(String title, String shortMsg, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushPushRequest req = new CloudpushPushRequest();
		req.setTarget("all");
		req.setTargetValue("all");
		req.setBody(msg);
		req.setDeviceType(0L);
		req.setIosBadge("1");
		req.setIosExtParameters("{'_ENV_','DEV'}");// 如果ios要发到开发环境，需要设置
													// iosExtParameter("_ENV_","DEV")
													// 大写！
		req.setIosMusic("default");
		req.setRemind(true);
		req.setStoreOffline(false);
		req.setSummery(shortMsg);
		req.setTimeout(72L);
		req.setTitle(title);
		try {
			CloudpushPushResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean IOSpushToAccount(String[] usernames, String title, String shortMsg, String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		CloudpushPushRequest req = new CloudpushPushRequest();
		req.setTarget("account");
		req.setTargetValue(array2str(usernames));
		req.setBody(msg);
		req.setDeviceType(0L);
		req.setIosBadge("1");
		req.setIosExtParameters("{'_ENV_','DEV'}");// 如果ios要发到开发环境，需要设置
													// iosExtParameter("_ENV_","DEV")
													// 大写！
		req.setIosMusic("default");
		req.setRemind(true);
		// req.setStoreOffline(true);
		req.setSummery(shortMsg);
		req.setTimeout(72L);
		req.setTitle(title);
		try {
			CloudpushPushResponse response = client.execute(req);
			if (response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
//		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//		CloudpushMessageAndroidRequest req = new CloudpushMessageAndroidRequest();
//		req.setBody("推送一个试试，走你");
//		req.setTarget("all");
//		req.setTargetValue("all");
//		try {
//			CloudpushMessageAndroidResponse response = client.execute(req);
//			System.out.println(response.getBody());
//			if (response.isSuccess()) {
//				System.out.println("push message is success!");
//			}
//		} catch (Exception e) {
//			System.out.println("push message is error!");
//		}
		System.out.println(IOSpushMessageToAll("测试滴滴滴滴滴滴滴滴滴"));
		System.out.println(IOSpushNoticeToAll("测试噢噢噢噢噢噢噢噢滴滴滴滴滴滴滴滴滴"));
		System.out.println(IOSpushToAll("测试", "噢噢噢噢噢噢噢噢", "滴滴滴滴滴滴滴滴滴"));
	}
}
