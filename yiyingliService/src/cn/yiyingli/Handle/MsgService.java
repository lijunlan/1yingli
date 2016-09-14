package cn.yiyingli.Handle;

import net.sf.json.JSONObject;

public abstract class MsgService {

	private String resMsg = "{\"state\":\"unknown error\"}";
	private JSONObject inMap;

	protected MsgService() {
	}

	protected String getResMsg() {
		return resMsg;
	}

	public boolean setDataMap(JSONObject data) {
		inMap = data;
		return checkData();
	}

	protected JSONObject getData() {
		return inMap;
	}

	protected abstract boolean checkData();

	public boolean validate() {
		return true;
	}

	public abstract void doit();

	protected void setResMsg(String msg) {
		resMsg = msg;
	}

	public String getResponseMsg() {
		return resMsg;
	}
}
