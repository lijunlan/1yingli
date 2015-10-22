package cn.yiyingli.Handle;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;


import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;

public class MessageHandle {

	/**
	 * deal with the application
	 * 
	 * @param rq
	 * @param rp
	 * @param context
	 */
	public static void deal(HttpServletRequest rq, HttpServletResponse rp, ApplicationContext context) {
		MessageHandle mHandle = new MessageHandle(rq, rp, context);
		mHandle.start();
		mHandle.doit();
	}

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private MsgService util;
	private ApplicationContext applicationContext;
	private Map<String, Object> data;

	private MessageHandle(HttpServletRequest rq, HttpServletResponse rp, ApplicationContext context) {
		req = rq;
		resp = rp;
		applicationContext = context;
	}

	/**
	 * get json data from the httpRequest's content
	 * 
	 * @param rq
	 * @return
	 */
	private static String getJson(HttpServletRequest rq) {
		String receive = "";
		try {
			 receive = org.apache.commons.io.IOUtils.toString(rq.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LogUtil.info("receive->>>" + receive, MessageHandle.class);
		// System.out.println(sBuffer.toString());
		return receive;
	}

	/**
	 * mission start<br/>
	 * choose method to deal with the application by the <b>style</b> field
	 */
	private void start() {
		data = Json.getMapPro(getJson(req));
		Map<String, Map<String, String>> configData = ConfigurationXmlUtil.getInstance().getConfigData();
		if (data.containsKey("style") && data.containsKey("method")) {
			String style = (String) data.get("style");
			String method = (String) data.get("method");
			if (configData.containsKey(style)) {
				Map<String, String> methodData = configData.get(style);
				if (methodData.containsKey(method)) {
					util = (MsgService) applicationContext.getBean(methodData.get(method));
					data.put("IP", req.getRemoteAddr());
				} else {
					returnError(MsgUtil.getErrorMsg("method is not acurate"));
				}
			} else {
				returnError(MsgUtil.getErrorMsg("style is not acurate"));
			}

		} else {
			returnError(MsgUtil.getErrorMsg("the style or method field is not included"));
		}
	}

	/**
	 * use relevant tool to process it<br/>
	 * return error message if data is incomplete
	 */
	private void doit() {
		if (util != null) {
			if (util.setDataMap(data)) {
				util.doit();
				returnMsg(util.getResponseMsg());
			} else {
				returnError(MsgUtil.getErrorMsg("data is incomplete"));
			}
		}
	}

	/**
	 * return message through httpResponse
	 * 
	 * @param msg
	 */
	private void returnMsg(String msg) {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		try {
			OutputStream stream = resp.getOutputStream();
			// System.out.println(msg);
			LogUtil.info("send---->>>" + msg, MessageHandle.class);
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * return error message
	 * 
	 * @param msg
	 */
	private void returnError(String msg) {
		returnMsg(msg);
	}
}
