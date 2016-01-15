package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.WarnUtil;
import cn.yiyingli.Weixin.Util.WXMessageUtil;

public class WXNotifyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5952254479886005690L;

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

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String receive = "";
		try {
			receive = org.apache.commons.io.IOUtils.toString(req.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LogUtil.info("receive->>>" + receive, WXNotifyServlet.class);
		if (WXMessageUtil.checkMsg(receive)) {
			String result = WXMessageUtil.getContent(receive, "result_code");
			if ("SUCCESS".equals(result)) {
				String oid = WXMessageUtil.getContent(receive, "out_trade_no");
				String money = WXMessageUtil.getContent(receive, "total_fee");
				OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");
				NotificationService notificationService = (NotificationService) getApplicationContext()
						.getBean("notificationService");
				Order order = orderService.queryByOrderNo(oid);
				if (order != null) {
					if (Float.valueOf(money) / 100F == order.getMoney().floatValue()) {
						String state = order.getState().split(",")[0];
						if (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
							order.setState(
									cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
							finishOrder(orderService, order);

							NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
									"尊敬的用户，订单号为" + order.getOrderNo() + "的订单已经付款完成，请等待导师接受订单", order.getCreateUser(),
									notificationService);
							NotifyUtil.notifyTeacher(order.getTeacher().getPhone(),
									order.getTeacher().getEmail(), "尊敬的导师，订单号为" + order.getOrderNo() + "的订单，用户("
											+ order.getCustomerName() + ")已经付款，等待您的接受。",
									order.getTeacher(), notificationService);
							finishOrder(orderService, order);
						} else {
							LogUtil.error("TRADE_SUCCESS order id:" + oid + ", state is wrong", this.getClass());
							WarnUtil.sendWarnToCTO("TRADE_SUCCESS order id:" + oid + ", state is wrong");
						}
					} else {
						LogUtil.error("TRADE_SUCCESS order id:" + oid + ", price is wrong, it should be "
								+ order.getMoney() + ", but it is " + Float.valueOf(money) / 100F, this.getClass());
						order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL + "," + order.getState());
						WarnUtil.sendWarnToCTO("order id:" + oid + ", price is wrong, it should be " + order.getMoney()
								+ ", but it is " + Float.valueOf(money) / 100F);
						orderService.update(order, false);
					}
				}
			}
			returnSuccess(resp);
		} else {
			LogUtil.error(receive, WXNotifyServlet.class);
			returnSuccess(resp);
		}
	}

	private void returnSuccess(HttpServletResponse resp) throws IOException, UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		OutputStream stream = resp.getOutputStream();
		stream.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>"
				.getBytes("UTF-8"));
	}

	private void finishOrder(OrderService orderService, Order order) {
		order.setPayMethod(OrderService.ORDER_PAYMETHOD_WEIXIN);
		orderService.updateAndPlusNumber(order);
	}
}
