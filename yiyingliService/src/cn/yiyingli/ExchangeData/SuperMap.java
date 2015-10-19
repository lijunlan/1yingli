package cn.yiyingli.ExchangeData;

import java.util.HashMap;
import java.util.Map;

import cn.yiyingli.Util.Json;


public class SuperMap {

	private Map<String, String> map = new HashMap<String, String>();

	public SuperMap() {

	}

	/**
	 * @return
	 */
	public Map<String, String> finish() {
		return map;
	}

	/**
	 * @return
	 */
	public String finishByJson() {
		return Json.getJson(map);
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
