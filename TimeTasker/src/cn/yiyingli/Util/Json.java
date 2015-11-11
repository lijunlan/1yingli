package cn.yiyingli.Util;

import java.io.BufferedReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class Json {

	/**
	 * 从servlet的request里面获取json字符串
	 * @param req
	 * @return
	 */
	public static String getJsonStringFromReq(HttpServletRequest req){
		StringBuffer json = new StringBuffer();  
        String line = null;  
        try {  
            BufferedReader reader = req.getReader();                  
            while((line = reader.readLine()) != null) {  
                json.append(line);                    
            }                 
        }  
        catch(Exception e) {  
            System.out.println(e.toString());  
        }  
        return json.toString(); 
	}
	/**
	 * 将json字符串转换成map
	 * @param json
	 * @return
	 */
	public static Map<String, String> getMap(String json) {
		try {
			@SuppressWarnings("unchecked")
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
}
