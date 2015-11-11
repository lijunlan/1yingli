package cn.yiyingli.Handle;

import java.util.Map;

public abstract class MsgService {

	private String resMsg = "{\"state\":\"unknown error\"}";
	private Map<String, Object> inMap;

	protected Map<String, Object> getInMap() {
		return inMap;
	}

	protected void setInMap(Map<String, Object> inMap) {
		this.inMap = inMap;
	}

	protected MsgService() {
	}

	protected String getResMsg() {
		return resMsg;
	}

	public boolean setDataMap(Map<String, Object> data) {
		inMap = data;
		return checkData();
	}

	protected Map<String, Object> getData() {
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
