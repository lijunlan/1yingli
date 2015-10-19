package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.getOrderInfoUtil;
import net.sf.json.JSONObject;

public class MGetOrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, String> send = Json.getMap(getJson(req));
		send.put("style", "manager");
		send.put("method", "getOrderList");
		String json = JSONObject.fromObject(send).toString();
		//json中有一个page，一个data：Json-list，一个state
		String result = getOrderInfoUtil.sendMsg(json);
		Map<String, String> map = Json.getMap(result);
		String msg;
		if (map.get("state").equals("success")) {
			msg=Json.getJson(map);
		} else {
			msg = "{'state':'fail','data':'" + map.get("msg") + "'}";
		}
		resp.setCharacterEncoding("utf-8");
		OutputStream stream = resp.getOutputStream();
		stream.write(msg.getBytes("UTF-8"));
	}

	/**
	 * get json data from the httpRequest's content
	 * 
	 * @param rq
	 * @return
	 */
	private static String getJson(HttpServletRequest rq) {
		InputStream in = null;
		StringBuffer sBuffer = new StringBuffer();
		byte[] buffer = new byte[1024];
		try {
			in = rq.getInputStream();
			while (in.read(buffer) > 0) {
				sBuffer.append(new String(buffer));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String json = sBuffer.toString();
		return json;
	}
}
