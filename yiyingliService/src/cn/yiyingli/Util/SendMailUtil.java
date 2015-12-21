package cn.yiyingli.Util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

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

	public static boolean sendMail(String toEmail, String checkNo) {
		return sendCheckNo("【一英里】验证码", checkNo, toEmail);
	}

	public static void main(String[] args) {
		// for (int i = 1; i <= 10; i++) {
		// System.out.println(sendMessage("lijunlan@1yingli.cn", "fwefwe",
		// "呵呵呵呵呵呵呵" + i));
		// }
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "T8Idmm00U1mAwzcn",
				"cZQkX1saEq1eF2g1ADbnO2kcFlJxb9");
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName("notify@1yingli.net");
			request.setAddressType(0);
			request.setTagName("123");
			request.setReplyToAddress(true);
			request.setToAddress("lijunlan@1yingli.cn");
			request.setSubject("邮件主题");
			request.setHtmlBody("邮件正文");
			@SuppressWarnings("unused")
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	public static boolean sendMessage(String toEmail, String title, String message) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		// mailInfo.setMailServerHost("email-smtp.us-west-2.amazonaws.com");
		mailInfo.setMailServerHost("smtp.1yingli.cn");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);

		// 邮箱用户名
		// mailInfo.setUserName("AKIAJMTNHGS3FEHBYZ6Q");
		mailInfo.setUserName("notify@1yingli.cn");
		// 邮箱密码
		// mailInfo.setPassword("AvMBHttyRlsICNXNFIzyisUnsHNj+NKWjmAgdubYha4l");
		mailInfo.setPassword("Onemile123!@#");
		// 发件人邮箱
		mailInfo.setFromAddress("notify@1yingli.cn");
		// 收件人邮箱
		mailInfo.setToAddress(toEmail);
		// 邮件标题
		mailInfo.setSubject("【一英里】" + title);
		// 邮件内容
		StringBuffer buffer = new StringBuffer();
		buffer.append(HTML);
		buffer.append(message);
		buffer.append(HTML2);
		mailInfo.setContent(buffer.toString());

		// 发送邮件
		// SimpleMailSender sms = new SimpleMailSender();
		// 发送文体格式
		// sms.sendTextMail(mailInfo);
		// 发送html格式
		SimpleMailSender.sendHtmlMail(mailInfo);
		return true;
		// System.out.println("邮件发送完毕");
	}

	private static boolean sendCheckNo(String title, String checkNo, String toEmail) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		// mailInfo.setMailServerHost("email-smtp.us-west-2.amazonaws.com");
		mailInfo.setMailServerHost("smtp.1yingli.cn");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);

		// 邮箱用户名
		// mailInfo.setUserName("AKIAJMTNHGS3FEHBYZ6Q");
		mailInfo.setUserName("notify@1yingli.cn");
		// 邮箱密码
		// mailInfo.setPassword("AvMBHttyRlsICNXNFIzyisUnsHNj+NKWjmAgdubYha4l");
		mailInfo.setPassword("Onemile123!@#");
		// 发件人邮箱
		mailInfo.setFromAddress("notify@1yingli.cn");
		// 收件人邮箱
		mailInfo.setToAddress(toEmail);
		// 邮件标题
		mailInfo.setSubject(title);
		// 邮件内容
		StringBuffer buffer = new StringBuffer();
		buffer.append(HTML);
		buffer.append("				 您的验证码是<strong style=\"color:#000;font-size:60px;\">&nbsp;&nbsp;&nbsp;" + checkNo
				+ "&nbsp;&nbsp;&nbsp;</strong><small></small>【一英里】		");
		buffer.append(HTML2);
		mailInfo.setContent(buffer.toString());

		// 发送邮件
		// SimpleMailSender sms = new SimpleMailSender();
		// 发送文体格式
		// sms.sendTextMail(mailInfo);
		// 发送html格式
		SimpleMailSender.sendHtmlMail(mailInfo);
		return true;
		// System.out.println("邮件发送完毕");
	}
}
