package com.sdll18.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * This class deal with the problem about Json
 * 
 * @author SDLL18
 * 
 */
public class Json {

	/**
	 * convert json into Map
	 * 
	 * @param json
	 *            JSON字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getMap(String json) {
		try {
			Map<String, String> result = (Map<String, String>) JSONObject
					.toBean(JSONObject.fromObject(json), Map.class);
			for (String key : result.keySet()) {
				Object obj = result.get(key);
				if (obj.equals(JSONNull.getInstance())) {
					result.put(key, "null");
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * convert Map into Json
	 * 
	 * @param map
	 *            map
	 * @return
	 */
	public static String getJson(Map<String, String> map) {
		try {
			return JSONObject.fromObject(map).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <h1>convert json into List of String</h1> json data should be a array
	 * data
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getStringArr(String json) {
		try {
			return (List<String>) JSONArray.toList(JSONArray.fromObject(json),
					new String(), new JsonConfig());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * convert json into List of Map<br/>
	 * json data should be a array
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getMapArr(String json) {
		try {
			return (List<Map<String, String>>) JSONArray.toList(
					JSONArray.fromObject(json), new HashMap<String, String>(),
					new JsonConfig());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * convert list of map into json
	 * 
	 * @param list
	 * @return
	 */
	public static String getJsonFromListMap(List<Map<String, String>> list) {
		try {
			JSONArray array = JSONArray.fromObject(list);
			for (int i = 0; i < array.size(); i++) {
				array.set(i, JSONObject.fromObject(array.get(i)).toString());
			}
			return array.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * convert list of string into json
	 * 
	 * @param list
	 * @return
	 */
	public static String getJson(List<String> list) {
		try {
			return JSONArray.fromObject(list).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
