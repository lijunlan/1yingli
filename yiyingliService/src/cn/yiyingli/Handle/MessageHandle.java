package cn.yiyingli.Handle;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import net.sf.json.JSONObject;

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
		try {
			mHandle.start();
			mHandle.doit();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(
					RemoteIPUtil.getAddr(rq) + "=>" + mHandle.getData() == null ? "null" : mHandle.getData().toString(),
					MessageHandle.class);
			mHandle.returnError(MsgUtil.getErrorMsgByCode("00000"));
		}
	}

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private MsgService util;
	private ApplicationContext applicationContext;
	private JSONObject data;

	private MessageHandle(HttpServletRequest rq, HttpServletResponse rp, ApplicationContext context) {
		req = rq;
		resp = rp;
		applicationContext = context;
	}

	private JSONObject getData() {
		return data;
	}

	/**
	 * get json data from the httpRequest's content
	 * 
	 * @param rq
	 * @return
	 */
	private static String getStringData(HttpServletRequest rq) {
		String receive = "";
		try {
			receive = org.apache.commons.io.IOUtils.toString(rq.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LogUtil.info("receive->>>" + receive, MessageHandle.class);
		// System.out.println(sBuffer.toString());
		return receive.replaceAll("\b", "").replaceAll("\f", "");
	}

	/**
	 * mission start<br/>
	 * choose method to deal with the application by the <b>style</b> field
	 */
	private void start() {
		data = JSONObject.fromObject(getStringData(req));
		Map<String, Map<String, String>> configData = ConfigurationXmlUtil.getInstance().getConfigData();
		if (data.containsKey("style") && data.containsKey("method")) {
			String style = (String) data.get("style");
			String method = (String) data.get("method");
			if (configData.containsKey(style)) {
				Map<String, String> methodData = configData.get(style);
				if (methodData.containsKey(method)) {
					util = (MsgService) applicationContext.getBean(methodData.get(method));
					data.put("IP", RemoteIPUtil.getAddr(req));
				} else {
					returnError(MsgUtil.getErrorMsgByCode("00002"));
				}
			} else {
				returnError(MsgUtil.getErrorMsgByCode("00003"));
			}

		} else {
			returnError(MsgUtil.getErrorMsgByCode("00004"));
		}
	}

	/**
	 * use relevant tool to process it<br/>
	 * return error message if data is incomplete
	 */
	private void doit() {
		if (util != null) {
			if (util.setDataMap(data)) {
				if (util.validate()) {
					util.doit();
				}
				returnMsg(util.getResponseMsg());
			} else {
				returnError(MsgUtil.getErrorMsgByCode("00001"));
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
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
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
