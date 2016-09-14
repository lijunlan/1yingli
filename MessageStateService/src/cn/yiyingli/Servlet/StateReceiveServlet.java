package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.Util.LogUtil;

@SuppressWarnings("serial")
public class StateReceiveServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String command = req.getParameter("command");
		String msgid = req.getParameter("msgid");
		String da = req.getParameter("da");
		String dc = req.getParameter("dc");
		String sm = req.getParameter("sm");
		if (command == null || msgid == null || da == null || dc == null || sm == null || "".equals(command)
				|| "".equals(msgid) || "".equals(da) || "".equals(dc) || "".equals(sm)) {
			sendMsg(resp, "{'state':'failed','msg':'" + "parameter is not complete" + "'}");
			return;
		}
		if ("RT_REQUEST".equals(command)) {
			LogUtil.info("msgid:" + msgid + "\tda:" + da + "\tdc" + dc + "\tsm" + sm, getClass());
			sendMsg(resp, "{'state':'success'}");
		} else {
			LogUtil.error("receive wrong msg from " + req.getRemoteAddr() + "\t", getClass());
			return;
		}
	}

	private static void sendMsg(HttpServletResponse resp, String msg) {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			OutputStream stream = resp.getOutputStream();
			LogUtil.debug("send---->>>" + msg, StateReceiveServlet.class);
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
