package cn.yiyingli.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.yiyingli.ExchangeData.ExDataToShow;
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

	public static void main(String[] args) {
		String json = "{\"style\":\"function\",\"method\":\"createApplication\",\"uid\":\"02a55a32-f2b6-4d09-a068-c1711650ac5c\",\"application\":{\"service\":{\"title\":\"聊人生，谈理想\",\"time\":\"1.5\",\"price\":\"300\",\"content\":\"就是聊人生嘛\",\"reason\":\"我喜欢聊人生嘛\",\"advantage\":\"我能聊嘛\",\"tips\":[{\"id\":\"64\"},{\"id\":\"1\"}]},\"workExperience\":[{\"companyName\":\"杭州千询科技有限公司\",\"position\":\"首席技术总监\",\"description\":\"可以可以，不错不错\",\"startTime\":\"2015,6\",\"endTime\":\"2015,8\"}],\"studyExperience\":[{\"schoolName\":\"厦门大学\",\"degree\":\"本科\",\"major\":\"软件工程\",\"description\":\"舒服，安逸，巴适\",\"startTime\":\"2011,9\",\"endTime\":\"2015,7\"}],\"name\":\"李俊澜\",\"phone\":\"15659831720\",\"address\":\"杭州\",\"mail\":\"345693031@qq.com\"}}";
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JSONObject.fromObject(json);
		// getMapPro(json);
		// Iterator<String> it = map.keySet().iterator();
		// while (it.hasNext()) {
		// String k = it.next();
		// Object o = map.get(k);
		// System.out.println(o.toString());
		// }
		ergodicJSON(map);

	}

	@SuppressWarnings({ "unchecked" })
	public static void ergodicJSON(Object object) {
		if (object instanceof JSONObject) {
			Map<String, Object> map = (Map<String, Object>) object;
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String k = it.next();
				Object o = map.get(k);
				ergodicJSON(o);
			}
		} else if (object instanceof JSONArray) {
			List<Object> l = (List<Object>) object;
			for (Object obj : l) {
				ergodicJSON(obj);
			}
		} else {
			System.out.println(object);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapPro(String json) {
		return (Map<String, Object>) JSONObject.fromObject(json);
	}

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
			Map<String, String> result = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(json),
					Map.class);
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

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getComplexMap(String json) {
		try {
			Map<String, Object> result = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(json),
					Map.class);
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
			return (List<String>) JSONArray.toList(JSONArray.fromObject(json), new String(), new JsonConfig());
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
			return (List<Map<String, String>>) JSONArray.toList(JSONArray.fromObject(json),
					new HashMap<String, String>(), new JsonConfig());
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

	public static String getJsonByEx(List<?> list) throws Exception {
		List<String> temp = new ArrayList<String>();
		for (Object ex : list) {
			if (ex instanceof ExDataToShow<?>) {
				temp.add(((ExDataToShow<?>) ex).toJson());
			} else
				throw new Exception("it should impl ExDataToShow");
		}
		return getJson(temp);
	}
}
