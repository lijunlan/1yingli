package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.TimeTasker.Core;
import cn.yiyingli.Util.Json;

public class TaskReceiveServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String json = "";
	Map<String, String> parm;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws UnsupportedEncodingException, IOException {
		json = Json.getJsonStringFromReq(req);
		parm = Json.getMap(json);
		if (checkParm(parm)) {
			returnMessage(resp, "BAD PARAMETER");
			return;
		}
		if (Core.getApplicationContext() == null) {
			ApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(this.getServletContext());
			Core.setApplicationContext(ctx);
		}
		Core.setTask(parm);
		System.out.println("receive->>" + json);
		returnMessage(resp, "setTask success");
	}

	private boolean checkParm(Map<String, String> parm) {
		if (parm.get("kind") == null || parm.get("action") == null || parm.get("endTime") == null
				|| parm.get("data") == null)
			return true;
		else
			return false;
	}

	private void returnMessage(HttpServletResponse resp, String message)
			throws IOException, UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		OutputStream stream = resp.getOutputStream();
		stream.write(message.getBytes("UTF-8"));
	}

}
