package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.Alipay.AlipayConfig;
import cn.yiyingli.Alipay.AlipaySubmit;
import cn.yiyingli.ExchangeData.ExRewardForPay;
import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;

public class RewardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4931703101740670779L;

	private ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Map<String, String> parms = new HashMap<String, String>();

		UserMarkService userMarkService = (UserMarkService) getApplicationContext().getBean("userMarkService");
		RewardService rewardService = (RewardService) getApplicationContext().getBean("rewardService");

		// uid is can be choosed
		if (req.getParameter("teacherId") == null || req.getParameter("money") == null
				|| req.getParameter("teacherName") == null || req.getParameter("callback") == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("00001"));
			return;
		}
		LogUtil.info("receive>>ALIPAY>>reward--->teacherName:" + req.getParameter("teacherName") + "\t teacherId:"
				+ req.getParameter("teacherId") + "\t money:" + req.getParameter("money"), this.getClass());

		String teacherId = req.getParameter("teacherId");
		@SuppressWarnings("deprecation")
		String teacherName = URLDecoder.decode(req.getParameter("teacherName"));
		String money = req.getParameter("money");
		String uid = req.getParameter("uid");
		String passageId = req.getParameter("passageId");
		String callback = req.getParameter("callback");

		Long userId = null;
		String userName = null;
		if (uid != null) {
			LogUtil.info("receive>>ALIPAY>>reward--->uid:" + uid, this.getClass());
			User user = userMarkService.queryUser(uid);
			if (user != null) {
				userId = user.getId();
				userName = user.getName();
			}
		}

		// 订单名称
		String subject = "打赏-" + teacherName;
		// 必填
		float tempmoney = Float.valueOf(money);
		// 付款金额
		String total_fee = String.valueOf(tempmoney >= 0.01F ? tempmoney : 0.01F);
		// 必填

		// 订单描述
		String body = "打赏-" + teacherName;

		// 商品展示地址
		String show_url = passageId == null ? "http://www.1yingli.cn/teacher/" + req.getParameter("teacherId")
				: "http://www.1yingli.cn/passage/" + passageId;
				// 需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

		// 防钓鱼时间戳
		// String anti_phishing_key = query_timestamp();
		// // 若要使用请调用类文件submit中的query_timestamp函数
		// if (anti_phishing_key.equals("")) {
		// returnMsg(resp, "<html>防钓鱼功能启动失败，请重试</html>");
		// return;
		// }

		// 客户端的IP地址
		// String exter_invoke_ip = RemoteIPUtil.getAddr(req);
		// 非局域网的外网IP地址，如：221.0.0.1

		Reward reward = new Reward();
		String time = Calendar.getInstance().getTimeInMillis() + "";
		reward.setCreateTime(time);
		reward.setFinishPay(false);
		reward.setFinishSalary(false);
		reward.setMoney(Float.valueOf(money));
		reward.setTeacherId(Long.valueOf(teacherId));
		reward.setTeacherName(teacherName);
		reward.setUserId(userId);
		reward.setUserName(userName);

		reward.setPassageId(passageId == null ? null : Long.valueOf(passageId));

		rewardService.save(reward);

		String oid = ExRewardForPay.getRewardNo(reward.getId());

		parms.put("service", "create_direct_pay_by_user");
		parms.put("partner", AlipayConfig.partner);
		parms.put("seller_email", AlipayConfig.seller_email);
		parms.put("_input_charset", AlipayConfig.input_charset);
		parms.put("payment_type", AlipayConfig.payment_type);
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			parms.put("notify_url", AlipayConfig.notify_url_debug);
		} else {
			parms.put("notify_url", AlipayConfig.notify_url);
		}

		parms.put("return_url", callback);

		parms.put("out_trade_no", oid);
		parms.put("subject", "【一英里】" + subject);
		parms.put("total_fee", total_fee);
		parms.put("body", body);
		parms.put("show_url", show_url);
		// parms.put("anti_phishing_key", anti_phishing_key);
		// parms.put("exter_invoke_ip", exter_invoke_ip);
		parms.put("extra_common_param", ExRewardForPay.getExtraParams(reward.getId(), Long.valueOf(teacherId),
				teacherName, money, uid, passageId == null ? null : Long.valueOf(passageId), userId, userName));
		// 过期时间 24h
		parms.put("it_b_pay", "24h");

		String sHtmlText = AlipaySubmit.buildRequest(parms, "get", "确认");
		sHtmlText = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<title>支付宝</title>" + "</head>" + sHtmlText + "<body></body></html>";
		returnMsg(resp, sHtmlText);
	}

	public static String query_timestamp() {
		String stamp = "";
		boolean flag = false;
		int count = 1;
		while (!flag) {
			try {
				stamp = AlipaySubmit.query_timestamp();
				flag = true;
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
				count++;
				if (count > 10) {
					break;
				}
			}
		}
		return stamp;
	}

	/**
	 * return message through httpResponse
	 * 
	 * @param msg
	 */
	private void returnMsg(HttpServletResponse resp, String msg) {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		try {
			OutputStream stream = resp.getOutputStream();
			LogUtil.info("send>>>>>" + msg, this.getClass());
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
