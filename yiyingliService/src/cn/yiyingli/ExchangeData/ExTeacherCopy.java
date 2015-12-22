package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Order;
import net.sf.json.JSONObject;

/**
 * @author Administrator<br/>
 * 
 *         teacherCopy<br/>
 *         name<br/>
 *         phone<br/>
 *         email<br/>
 *         username<br/>
 *
 */
public class ExTeacherCopy {
	
	public static String getTeacherIconUrl(Order order) {
		String jsonTeacher = order.getTeacherInfo();
		JSONObject teacher = JSONObject.fromObject(jsonTeacher);
		return teacher.getString("iconUrl");
	}

	public static String getTeacherName(Order order) {
		String jsonTeacher = order.getTeacherInfo();
		JSONObject teacher = JSONObject.fromObject(jsonTeacher);
		return teacher.getString("name");
	}

	public static String getTeacherPhone(Order order) {
		String jsonTeacher = order.getTeacherInfo();
		JSONObject teacher = JSONObject.fromObject(jsonTeacher);
		return teacher.getString("phone");
	}

	public static String getTeacherEmail(Order order) {
		String jsonTeacher = order.getTeacherInfo();
		JSONObject teacher = JSONObject.fromObject(jsonTeacher);
		return teacher.getString("email");
	}

	public static String getTeacherUsername(Order order) {
		String jsonTeacher = order.getTeacherInfo();
		JSONObject teacher = JSONObject.fromObject(jsonTeacher);
		return teacher.getString("username");
	}

}
