package cn.yiyingli.recommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.yiyingli.util.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MainFunction {

	public static void main(String[] args) throws SQLException {
		// System.out.println(Calendar.getInstance().getTimeInMillis());
		updateTeacherData();
		// updateUserTrainDataRecord();
		// updateUserTrainDataLike();
		// updateUserTrainDataOrder();
		// editTeacherData();
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		dest = dest.replaceAll("'", "\"");
		return dest.replaceAll("\b", "");
	}

	/**
	 * 删除content里面的h1标签的样式
	 */
	private static void editTeacherData() {
		for (int i = 121; i <= 121; i++) {
			JSONObject send = new JSONObject();
			send.put("style", "teacher");
			send.put("method", "getAllInfo");
			send.put("teacherId", i + "");
			String result = MsgUtil.sendMsgToService(send.toString());
			JSONObject receive = JSONObject.fromObject(result);
			if (!receive.getString("state").equals("error")) {
				String content = receive.getString("serviceContent");
				int start = content.indexOf("<h1") + 3;
				int end = content.indexOf(">", start);
				System.out.println(start + " " + end);
				if (start < 0 || end < 0 || start > end)
					continue;
				content = content.replace(content.substring(start, end), "");
				content = replaceBlank(content);
				System.out.println("");
				System.out.println(content);
				String sql;
				Connection conn = null;
				String url = "jdbc:mysql://yiyingli.mysql.rds.aliyuncs.com:3306/fortest?user=sdll18&password=ll1992917&useUnicode=true&characterEncoding=UTF8";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(url);
					Statement stmt = conn.createStatement();
					sql = "update tservice set tservice.CONTENT='" + content + "' where tservice.TEACHER_ID="
							+ receive.getString("teacherId") + ";";
					System.out.println(sql);
					int rs = stmt.executeUpdate(sql);
					System.out.println(rs);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void updateUserTrainDataOrder() {
		String sql;
		Connection conn = null;
		String url = "jdbc:mysql://yiyingli.mysql.rds.aliyuncs.com:3306/yiyingli?user=sdll18&password=ll1992917&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			sql = "select * from orders ;";
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray sendList = new JSONArray();
			while (rs.next()) {
				String uid = rs.getString("USER_ID");
				String tid = rs.getString("TEACHER_ID");
				String time = rs.getString("CREATETIME");
				sendList.add(JSONUserTrainData(uid, tid, "rate", (short) 5, time.equals("") ? 0L : Long.valueOf(time)));
			}
			System.out.println(sendList.size());
			MsgUtil.sendMsgToBaiduTrainData(sendList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void updateUserTrainDataLike() {
		String sql;
		Connection conn = null;
		String url = "jdbc:mysql://yiyingli.mysql.rds.aliyuncs.com:3306/yiyingli?user=sdll18&password=ll1992917&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			sql = "select * from userliketeacher ;";
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray sendList = new JSONArray();
			while (rs.next()) {
				String uid = rs.getString("USER_ID");
				String tid = rs.getString("TEACHER_ID");
				String time = rs.getString("CREATETIME");
				sendList.add(JSONUserTrainData(uid, tid, "rate", (short) 3, time.equals("") ? 0L : Long.valueOf(time)));
			}
			System.out.println(sendList.size());
			MsgUtil.sendMsgToBaiduTrainData(sendList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void updateTeacherData() {
		JSONArray jarray = new JSONArray();
		for (int i = 240; i <= 240; i++) {
			JSONObject obj = updateTeacherData(i + "");
			if (obj != null) {
				jarray.add(obj);
			}
		}
		MsgUtil.sendMsgToBaidu(jarray.toString());
	}

	private static void updateUserTrainDataRecord() {
		String sql;
		Connection conn = null;
		String url = "jdbc:mysql://yiyingli.mysql.rds.aliyuncs.com:3306/yiyingli?user=sdll18&password=ll1992917&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			sql = "select * from record where record.DATA like '%userId%';";
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray sendList = new JSONArray();
			while (rs.next()) {
				String data = rs.getString("DATA");
				String time = rs.getString("CREATETIME");
				int s = data.indexOf("=");
				int e = data.indexOf("u");
				String tid = data.substring(s + 1, e);
				s = data.lastIndexOf("=");
				String uid = data.substring(s + 1);
				sendList.add(JSONUserTrainData(uid, tid, "click", (short) 0, Long.valueOf(time)));
			}
			System.out.println(sendList.size());
			MsgUtil.sendMsgToBaiduTrainData(sendList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static JSONObject JSONUserTrainData(String uid, String tid, String action, short rate, long time) {
		JSONObject toBaidu = new JSONObject();
		toBaidu.put("UserId", uid);
		toBaidu.put("ItemId", tid);
		toBaidu.put("Action", action);
		if (action.equals("rate")) {
			JSONObject jsonContext = new JSONObject();
			jsonContext.put("Score", rate);
			toBaidu.put("Context", jsonContext);
		} else {
			toBaidu.put("Context", new JSONObject());
		}
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		toBaidu.put("Timestamp", formatter.format(new Date(time)));
		return toBaidu;
	}

	private static JSONObject updateTeacherData(String tid) {
		JSONObject send = new JSONObject();
		send.put("style", "teacher");
		send.put("method", "getAllInfo");
		send.put("teacherId", tid);
		String result = MsgUtil.sendMsgToService(send.toString());
		JSONObject receive = JSONObject.fromObject(result);
		if (receive.getString("state").equals("success")) {
			String title = receive.getString("serviceTitle");
			String content = receive.getString("introduce");
			JSONArray jsonLabels = new JSONArray();
			JSONArray we = JSONArray.fromObject(receive.getString("workExperience"));
			for (Object obj : we) {
				JSONObject jobj = (JSONObject) obj;
				JSONObject jsonLabel = new JSONObject();
				jsonLabel.put("Label", jobj.get("companyName"));
				jsonLabel.put("Weight", 5);
				jsonLabels.add(jsonLabel);
				jsonLabel = new JSONObject();
				jsonLabel.put("Label", jobj.get("position"));
				jsonLabel.put("Weight", 2);
				jsonLabels.add(jsonLabel);
			}
			JSONArray se = JSONArray.fromObject(receive.getString("studyExperience"));
			for (Object obj : se) {
				JSONObject jobj = (JSONObject) obj;
				JSONObject jsonLabel = new JSONObject();
				jsonLabel.put("Label", jobj.get("schoolName"));
				jsonLabel.put("Weight", 5);
				jsonLabels.add(jsonLabel);
				jsonLabel = new JSONObject();
				jsonLabel.put("Label", jobj.get("major"));
				jsonLabel.put("Weight", 3);
				jsonLabels.add(jsonLabel);
				jsonLabel = new JSONObject();
				jsonLabel.put("Label", jobj.get("degree"));
				jsonLabel.put("Weight", 2);
				jsonLabels.add(jsonLabel);
			}
			JSONArray tip = JSONArray.fromObject(receive.getString("tips"));
			JSONArray jsonCategory = new JSONArray();
			for (Object obj : tip) {
				JSONObject jobj = (JSONObject) obj;
				jsonCategory.add(jobj.getString("id"));
			}
			String Auxiliary = receive.getString("simpleinfo");
			JSONObject toBaidu = new JSONObject();
			toBaidu.put("Version", 1.0);
			toBaidu.put("ItemId", tid);
			toBaidu.put("DisplaySwitch", "On");
			toBaidu.put("Url", "http://www.1yingli.cn/personal.html?tid=" + tid);
			JSONObject jsonIndexed = new JSONObject();
			jsonIndexed.put("Title", title);
			jsonIndexed.put("Content", content);
			jsonIndexed.put("Labels", jsonLabels);
			toBaidu.put("Indexed", jsonIndexed);
			JSONObject jsonProperties = new JSONObject();
			jsonProperties.put("Quality", 1);
			jsonProperties.put("Category", jsonCategory);
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			jsonProperties.put("CreateTime", formatter.format(new Date()));
			toBaidu.put("Properties", jsonProperties);
			toBaidu.put("Auxiliary", Auxiliary);
			return toBaidu;
		}
		return null;
	}

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
