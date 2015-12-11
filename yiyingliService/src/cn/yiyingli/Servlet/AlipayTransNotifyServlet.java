package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.Alipay.AlipayNotify;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.WarnUtil;

@SuppressWarnings("serial")
public class AlipayTransNotifyServlet extends HttpServlet {

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
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = req.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 批量付款数据中转账成功的详细信息

		String success_details = new String(req.getParameter("success_details").getBytes("ISO-8859-1"), "UTF-8");

		// 批量付款数据中转账失败的详细信息
		String fail_details = new String(req.getParameter("fail_details").getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if (AlipayNotify.verify(params)) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// String time = req.getParameter("notify_time");
			// String type = req.getParameter("notify_type");
			// String id = req.getParameter("notify_id");
			// String sign_type = req.getParameter("sign_type");
			// String sign = req.getParameter("sign");
			// String no = req.getParameter("batch_no");
			// String pay_user_id = req.getParameter("pay_user_id");
			// String pay_account_no = req.getParameter("pay_account_no");
			String s_records[] = success_details.split("\\|");
			// String f_records[] = fail_details.split("\\|");
			OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");
			for (String record : s_records) {
				String item[] = record.split("^");
				if ("S".equals(item[4])) {
					String orderNo = item[0].substring(8);
					String alipayNo = item[1];
					String name = item[2];
					String money = item[3];
					Order order = orderService.queryByOrderNo(orderNo);
					if (!((alipayNo.equals(order.getAlipayNo()) || alipayNo.equals(order.getTeacher().getAlipay()))
							&& name.equals(order.getTeacher().getName())
							&& (Float.valueOf(money) == Float.valueOf(order.getOriginMoney())))) {
						WarnUtil.sendWarnToCTO("支付订单出错,信息不符合：" + record);
						LogUtil.error("支付订单出错,信息不符合：" + record, getClass());
						continue;
					}
					if (order.getSalaryState() == OrderService.ORDER_SALARY_STATE_NEED) {
						order.setSalaryState(OrderService.ORDER_SALARY_STATE_DONE);
						orderService.update(order, false);
					} else {
						WarnUtil.sendWarnToCTO("支付订单出错,收到支付宝通知的时候，支付状态为已经支付：" + record);
						LogUtil.error("支付订单出错,收到支付宝通知的时候，支付状态为已经支付：" + record, getClass());
					}
				} else {
					WarnUtil.sendWarnToCTO("支付订单出错,收到支付失败订单：" + record);
					LogUtil.error("支付订单出错,收到支付失败订单：" + record, getClass());
				}

			}
			//////////////////////////////////////////////////////////////////////////////////////////
			returnMsg(resp, "success");
		} else {// 验证失败
			WarnUtil.sendWarnToCTO("收到支付失败订单:" + fail_details);
			LogUtil.error(fail_details, getClass());
			returnMsg(resp, "fail");
		}
	}

	/**
	 * return message through httpResponse
	 * 
	 * @param msg
	 * @throws IOException
	 */
	private void returnMsg(HttpServletResponse resp, String msg) throws IOException {
		resp.setCharacterEncoding("utf-8");
		OutputStream stream = resp.getOutputStream();
		stream.write(msg.getBytes("UTF-8"));
	}
}
