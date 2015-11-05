package cn.yiyingli.Util;

import cn.yiyingli.ExchangeData.SuperMap;

public class MsgUtil {

	public static String getErrorMsg(String msg) {
		return "{\"state\":\"error\",\"msg\":\"" + msg + "\"}";
	}

	public static String getErrorMsgByCode(String errCode) {
		String msg = ErrorTransformUtil.getInstance().getSettingData().get(errCode);
		if (msg == null)
			msg = "unknown";
		return new SuperMap().put("state", "error").put("errCode", errCode).put("msg", "").finishByJson();
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
