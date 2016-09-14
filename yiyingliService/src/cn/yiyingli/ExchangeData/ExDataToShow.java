package cn.yiyingli.ExchangeData;

import net.sf.json.JSONObject;

public interface ExDataToShow<T> {

	public String toJson();
	
	public JSONObject finish();

	public void setUpByPersistant(T persistant);

}
