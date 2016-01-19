package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
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
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;

public class AlipayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3089800443291248718L;

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

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Map<String, String> parms = new HashMap<String, String>();

		UserMarkService userMarkService = (UserMarkService) getApplicationContext().getBean("userMarkService");
		OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");

		if (req.getParameter("oid") == null || req.getParameter("uid") == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("00001"));
			return;
		}
		LogUtil.info("receive>>>>orderId:" + req.getParameter("oid") + "\t uid:" + req.getParameter("uid"),
				this.getClass());

		// 商户订单号
		String oid = req.getParameter("oid");
		// 商户网站订单系统中唯一订单号，必填
		Order order = orderService.queryByShowId(oid, false);
		if (order == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		// 用户id
		String uid = req.getParameter("uid");
		User user = userMarkService.queryUser(uid);
		if (user == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("14001"));
			return;
		}

		if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
			LogUtil.info("receive>>>>createOrderId:" + order.getCreateUser().getId() + ",userId:" + user.getId() + ","
					+ (order.getCreateUser().getId() == user.getId()), this.getClass());
			returnMsg(resp, MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		String state = order.getState();
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		// 订单名称
		String subject = order.getServiceTitle();
		// 必填

		// 付款金额
		String total_fee = String.valueOf(order.getMoney());
		// 必填

		// 订单描述
		String body = order.getServiceTitle();

		// 商品展示地址
		String show_url = "http://www.1yingli.cn/teacher/" + order.getTeacher().getId();
		// 需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

		// 防钓鱼时间戳
		// String anti_phishing_key = query_timestamp();
		// 若要使用请调用类文件submit中的query_timestamp函数
		// if (anti_phishing_key.equals("")) {
		// returnMsg(resp, "<html>防钓鱼功能启动失败，请重试</html>");
		// return;
		// }

		// 客户端的IP地址
		// String exter_invoke_ip = RemoteIPUtil.getAddr(req);

		// 非局域网的外网IP地址，如：221.0.0.1

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
		// 判断是否使用默认的return_url
		if (req.getParameter("callback") == null) {
			parms.put("return_url", AlipayConfig.return_url);
		} else {
			parms.put("return_url", req.getParameter("callback"));
		}
		parms.put("out_trade_no", oid);
		parms.put("subject", "【一英里】[" + order.getTeacher().getName() + "]" + subject);
		parms.put("total_fee", total_fee);
		parms.put("body", body);
		parms.put("show_url", show_url);

		// parms.put("anti_phishing_key", anti_phishing_key);
		// parms.put("exter_invoke_ip", exter_invoke_ip);

		// 过期时间 24h
		parms.put("it_b_pay", "24h");

		String sHtmlText = AlipaySubmit.buildRequest(parms, "get", "确认");
		sHtmlText = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<title>支付宝</title>" + "</head>" + sHtmlText + "<body></body></html>";
		returnMsg(resp, sHtmlText);
	}

	public static void main(String[] args) {
		System.out.println(query_timestamp());
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
