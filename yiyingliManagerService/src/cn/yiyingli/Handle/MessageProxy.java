package cn.yiyingli.Handle;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;

import cn.yiyingli.Persistant.MidKeeper;
import cn.yiyingli.Persistant.OperateRecord;
import cn.yiyingli.Service.MidKeeperService;
import cn.yiyingli.Service.OperateService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.LogUtil;

public class MessageProxy {

	private OperateService operateService;
	
	private MidKeeperService midKeeperService;

	public MidKeeperService getMidKeeperService() {
		return midKeeperService;
	}

	public void setMidKeeperService(MidKeeperService midKeeperService) {
		this.midKeeperService = midKeeperService;
	}

	public OperateService getOperateService() {
		return operateService;
	}

	public void setOperateService(OperateService operateService) {
		this.operateService = operateService;
	}

	public static void deal(HttpServletRequest request, HttpServletResponse respense, ApplicationContext apc) {
		String json = getJson(request);
		Map<String, Object> dataReceive = Json.getMapPro(json);
		// 记录一部分信息
		OperateRecord or = new OperateRecord();
		MidKeeper mk = new MidKeeper();
		
		or.setTimeMills(System.currentTimeMillis());
		or.setIp(request.getRemoteAddr());
		or.setOperate((String) dataReceive.get("method"));
		or.setReceive(json);

		System.out.println("ReceiveAndRetransmission>>" + json);

		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://service.1yingli.cn/yiyingliService/manage");
		post.addHeader(HTTP.CONTENT_TYPE, "text/json");
		StringEntity se = new StringEntity(json, "utf-8");
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "utf-8"));
		post.setEntity(se);
		String result = "";
		try {
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("receiveFromYiyingli>>" + result);

		returnMsg(respense, result);
		//解析一英里返回数据
		Map<String, Object> dataSend = Json.getMapPro(result);
		MidKeeperService mks = (MidKeeperService) apc.getBean("midKeeperService");
		//登录失效删除记录
		if(dataSend.get("msg")!=null && ((String)dataSend.get("msg")).equals("manager is not existed")){
			mks.remove(dataReceive.get("mid").toString());
		}
		if(dataSend.get("mid")!=null){
			//管理员登录
			mk.setId(dataSend.get("mid").toString());
			mk.setName(dataSend.get("mname").toString());
			mks.save(mk);
			or.setName(mk.getName());
		}else{
			String mname = mks.find(dataReceive.get("mid").toString());
			if(mname == null){
				mname = "";
			}
			or.setName(mname);
		}
		//管理员退出
		if(((String)dataReceive.get("method")).equals("logout")){
			mks.remove(dataReceive.get("mid").toString());
		}
		or.setResult(dataSend.get("state") + ":" + dataSend.get("msg"));
		((OperateService) apc.getBean("operateService")).save(or);
	}

	
	/**
	 * get json data from the httpRequest's content
	 * 
	 * @param rq
	 * @return String
	 */
	private static String getJson(HttpServletRequest rq) {
		String receive = "";
		try {
			receive = org.apache.commons.io.IOUtils.toString(rq.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LogUtil.info("receive->>>" + receive, MessageProxy.class);
		return receive;
	}

	/**
	 * 返回信息
	 * @param msg
	 */
	private static void returnMsg(HttpServletResponse response,String msg) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			OutputStream stream = response.getOutputStream();
			LogUtil.info("send---->>>" + msg, MessageProxy.class);
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
