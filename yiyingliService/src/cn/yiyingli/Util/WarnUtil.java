package cn.yiyingli.Util;

public class WarnUtil {

	private static final String PHONE = "15659831720";

	public static void sendWarnToCTO(String msg) {
		SendMessageUtil.sendMessage(PHONE, "WARN:" + msg);
	}

}
