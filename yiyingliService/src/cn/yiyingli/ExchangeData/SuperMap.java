package cn.yiyingli.ExchangeData;

import java.util.Map;

import net.sf.json.JSONObject;


public class SuperMap {

	private JSONObject map =new JSONObject();

	public SuperMap() {

	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> finish() {
		return map;
	}

	/**
	 * @return
	 */
	public String finishByJson() {
		return map.toString();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public SuperMap put(String key, String value) {
		if (value == null)
			value = "";
		map.put(key, value);
		return this;
	}

	public SuperMap put(String key, Integer value) {
		if (value == null)
			map.put(key, "");
		else
			map.put(key, String.valueOf(value));
		return this;
	}

	public SuperMap put(String key, Boolean value) {
		if (value == null)
			map.put(key, "");
		else
			map.put(key, String.valueOf(value));
		return this;
	}

	public SuperMap put(String key, Object value) {
		if (value == null)
			map.put(key, "");
		else
			map.put(key, String.valueOf(value));
		return this;
	}

}
