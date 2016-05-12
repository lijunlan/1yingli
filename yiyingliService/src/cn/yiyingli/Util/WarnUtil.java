package cn.yiyingli.Util;

public class WarnUtil {

	private static final String PHONE = "15659831720";

	private static final String PHONE2 = "18964959647";

	public static void sendWarnToCTO(String msg) {
		SendMessageUtil.sendMessage(PHONE, "WARN:" + msg);
		SendMessageUtil.sendMessage(PHONE2, "WARN:" + msg);
	}

}
