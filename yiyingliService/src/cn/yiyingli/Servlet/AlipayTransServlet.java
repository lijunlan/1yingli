package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.Alipay.AlipayConfig;
import cn.yiyingli.Alipay.AlipaySubmit;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;

@SuppressWarnings("serial")
public class AlipayTransServlet extends HttpServlet {

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

		if (req.getParameter("oid") == null || req.getParameter("mid") == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("00001"));
			return;
		}
		LogUtil.info("receive>>>>orderId:" + req.getParameter("oid") + "\t mid:" + req.getParameter("mid"),
				this.getClass());

		ManagerMarkService managerMarkService = (ManagerMarkService) getApplicationContext()
				.getBean("managerMarkService");
		OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");

		String mid = req.getParameter("mid");
		Manager manager = managerMarkService.queryManager(mid);
		if (manager == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("34001"));
			return;
		}

		// 商户订单号
		String oid = req.getParameter("oid");
		// 商户网站订单系统中唯一订单号，必填
		Order order = orderService.queryByShowId(oid, false);
		if (order == null) {
			returnMsg(resp, MsgUtil.getErrorMsgByCode("42001"));
			return;
		}

		// 服务器异步通知页面路径
		String notify_url = "";
		if (!"false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
			notify_url = AlipayConfig.notify_url_debug;
		} else {
			notify_url = AlipayConfig.trans_notify_url;
		}
		// 需http://格式的完整路径，不允许加?id=123这类自定义参数

		// 付款账号
		String email = AlipayConfig.seller_email;
		// 必填

		// 付款账户名
		String account_name = AlipayConfig.seller_name;
		// 必填，个人支付宝账号是真实姓名公司支付宝账号是公司名称

		// 付款当天日期
		Calendar calendar = Calendar.getInstance();
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		String pay_date = "" + y + (m > 9 ? (m + "") : ("0" + m)) + (d > 9 ? (d + "") : ("0" + d));
		// 必填，格式：年[4位]月[2位]日[2位]，如：20100801

		// 批次号
		String batch_no = "" + y + (m > 9 ? (m + "") : ("0" + m)) + (d > 9 ? (d + "") : ("0" + d)) + order.getOrderNo();
		// 必填，格式：当天日期[8位]+序列号[3至16位]，如：201008010000001

		// 付款总金额
		String batch_fee = String.valueOf(order.getOriginMoney());
		// 必填，即参数detail_data的值中所有金额的总和

		// 付款笔数
		String batch_num = "1";
		// 必填，即参数detail_data的值中，“|”字符出现的数量加1，最大支持1000笔（即“|”字符出现的数量999个）

		// 付款详细数据
		String detail_data = batch_no + "^" + order.getAlipayNo() == null ? order.getTeacher().getAlipay()
				: order.getAlipayNo() + "^" + order.getTeacher().getName() + "^" + batch_fee + "^【一英里】"
						+ order.getServiceTitle();
						// 必填，格式：流水号1^收款方帐号1^真实姓名^付款金额1^备注说明1|流水号2^收款方帐号2^真实姓名^付款金额2^备注说明2....

		//////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "batch_trans_notify");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("email", email);
		sParaTemp.put("account_name", account_name);
		sParaTemp.put("pay_date", pay_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_fee", batch_fee);
		sParaTemp.put("batch_num", batch_num);
		sParaTemp.put("detail_data", detail_data);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		returnMsg(resp, sHtmlText);
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
