package cn.yiyingli.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.LinkedBlockingQueue;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.yiyingli.AliyunUtil.AliyunConfiguration;
import cn.yiyingli.ExchangeData.SuperMap;

public class SendMailUtil {

	private static final String HTML = "<html lang=\"ch\"><head><meta charset=\"utf-8\"><title>一英里</title><meta name=\"viewport\""
			+ "content=\"width=device-width, initial-scale=1.0,maximum-scale=1\">"
			+ "<meta name=\"description\" content=\"1.0\">"
			+ "<meta name=\"author\" content=\"sdll18\"></head><body><div class='container' style=\"width:595px;height:842px;margin:20px;background-image:url('http://www.1yingli.cn/mail/img/bg2.jpg');background-size:100% 100%\">"
			+ "	<div class='row clearfix'>		<div class='col-md-12 column' style=\"padding:10%;padding-top:20%\">			<h3 style=\"color:#666\">				亲爱的用户：			</h3><br />"
			+ "			<p style=\"color:#666\">";
	private static final String HTML2 = "</p>"
			+ "		<div style=\"margin-top:120px;color:#888;\">				<address style=\"font-size:12px;font-weight:normal;font-style:normal;\"> <strong>杭州千询科技有限公司</strong><br />浙江省杭州市拱墅区萍水街333号御峰大厦1516<br /> <abbr title='团队'>一英里团队</abbr><br /> "
			+ "<abbr title=\"网址\">网址:</abbr><a href=\"http://www.1yingli.cn\"  target=\"_Blank\" style=\"color:#333\">http://www.1yingli.cn</a></address>"
			+ "		</div>			</div>	</div>" + "</div></body></html>";

	public static void sendMail(String toEmail, String checkNo) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(HTML);
		buffer.append("				 您的验证码是<strong style=\"color:#000;font-size:60px;\">&nbsp;&nbsp;&nbsp;" + checkNo
				+ "&nbsp;&nbsp;&nbsp;</strong><small></small>【一英里】		");
		buffer.append(HTML2);
		SuperMap map = new SuperMap();
		map.put("content", buffer.toString());
		map.put("email", toEmail);
		map.put("title", "验证码");
		SendMailUtil sendMailUtil = SendMailUtil.getInstance();
		sendMailUtil.addSend(map);
	}

	private static SendMailUtil singleInstance;

	public static SendMailUtil getInstance() {
		if (singleInstance == null) {
			singleInstance = new SendMailUtil();
			singleInstance.start();
		}
		return singleInstance;
	}

	private LinkedBlockingQueue<SuperMap> sendQueue;

	public SendMailUtil() {
		sendQueue = new LinkedBlockingQueue<SuperMap>();
	}

	public void addSend(SuperMap map) {
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			return;
		}
		try {
			sendQueue.put(map);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		Thread sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						SuperMap map = sendQueue.take();
						boolean result = false;
						try {
							result = sendRequest(map);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						if (!result) {
							sendQueue.add(map);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendThread.start();
	}

	private boolean sendRequest(SuperMap map) throws UnsupportedEncodingException {
		String toEmail = map.finish().getString("email");
		String title = map.finish().getString("title");
		String data = map.finish().getString("content");
		return sendData(toEmail, title, data);
	}

	private boolean sendData(String toEmail, String title, String data) {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliyunConfiguration.ACCESS_ID,
				AliyunConfiguration.ACCESS_KEY);
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName("notify@1yingli.net");
			request.setAddressType(0);
			request.setTagName("【一英里】");
			request.setReplyToAddress(true);
			request.setToAddress(toEmail);
			request.setSubject("【一英里】" + title);
			// 邮件内容
			request.setHtmlBody(data);
			@SuppressWarnings("unused")
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void sendMessage(String toEmail, String title, String message) {
		SuperMap map = new SuperMap();
		map.put("title", title);
		map.put("email", toEmail);
		StringBuffer buffer = new StringBuffer();
		buffer.append(HTML);
		buffer.append(message);
		buffer.append(HTML2);
		map.put("content", buffer.toString());
		SendMailUtil sendMailUtil = SendMailUtil.getInstance();
		sendMailUtil.addSend(map);

	}

	public static void main(String[] args) throws IOException {
		// for (int i = 1; i <= 10; i++) {
		// System.out.println(sendMessage("lijunlan@1yingli.cn", "fwefwe",
		// "呵呵呵呵呵呵呵" + i));
		// }
		// sendMessage("lijunlan@1yingli.cn", "tetete", "569684957439543");
		// IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
		// "T8Idmm00U1mAwzcn",
		// "cZQkX1saEq1eF2g1ADbnO2kcFlJxb9");
		// IAcsClient client = new DefaultAcsClient(profile);
		// SingleSendMailRequest request = new SingleSendMailRequest();
		// try {
		// request.setAccountName("notify@1yingli.net");
		// request.setAddressType(0);
		// request.setTagName("123");
		// request.setReplyToAddress(true);
		// request.setToAddress("lijunlan@1yingli.cn");
		// request.setSubject("邮件主题");
		// request.setHtmlBody("邮件正文");
		// @SuppressWarnings("unused")
		// SingleSendMailResponse httpResponse = client.getAcsResponse(request);
		// } catch (ServerException e) {
		// e.printStackTrace();
		// } catch (ClientException e) {
		// e.printStackTrace();
		// }
		// InputStreamReader isr = new InputStreamReader(new
		// FileInputStream("F:/maldives2.txt"), "utf-8");
		// BufferedReader br = new BufferedReader(isr);
		// StringBuilder sb = new StringBuilder();
		// String temp = null;
		// while ((temp = br.readLine()) != null) {
		// sb.append(temp);
		// }
		// isr.close();
		// System.out.println(sb.toString());
		// sendData("345693031@qq.com", "【马尔代夫暑期(春季)志愿者项目详情】
		// 周日项目讲解QQ群号：531384809", sb.toString());
		// String message =
		// "尊敬的用户您好，由于近期陈彦含导师有大量人预约，导致其微信无法添加更多好友，麻烦大家通过微信号：clove930423，查找并添加好友，同时请备注上：姓名+订单号末尾4位数。带给您的不便请谅解，谢谢您对一英里的支持。";
		// InputStream is = new FileInputStream("F:/RR.xls");
		// ExcelReader excelReader = new ExcelReader();
		// List<List<String>> list = excelReader.getCellsByColNum(is, new int[]
		// { 0 });
		// for (int i = 2; i <= list.size(); i++) {
		// String email = list.get(i - 1).get(0);
		// if(!sendData(email, "【马尔代夫暑期(春季)志愿者项目详情】周日项目讲解QQ群号：531384809",
		// sb.toString())){
		// System.out.println(i);
		// };
		// }
	}

}
