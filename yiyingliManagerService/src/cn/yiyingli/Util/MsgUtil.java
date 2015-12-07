package cn.yiyingli.Util;

import cn.yiyingli.ExchangeData.SuperMap;

public class MsgUtil {

	public static String getErrorMsg(String msg) {
		return "{\"state\":\"error\",\"msg\":\"" + msg + "\"}";
	}

	public static String getSuccessMsg(String msg) {
		return "{\"state\":\"success\",\"msg\":\"" + msg + "\"}";
	}

	public static SuperMap getSuccessMap() {
		SuperMap tmp = new SuperMap();
		tmp.put("state", "success");
		return tmp;
	}
	
	public static SuperMap getErrorMap() {
		SuperMap tmp = new SuperMap();
		tmp.put("state", "error");
		return tmp;
	}
}
