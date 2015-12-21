package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.WarnUtil;

/**
 * 支付宝交易通知
 * 
 * @author lp
 *
 */
@SuppressWarnings("serial")
public class TradeNotifyProcessServlet extends HttpServlet {

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

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String[]> tmp = req.getParameterMap();
		for (Iterator<String> itr = tmp.keySet().iterator(); itr.hasNext();) {
			String name = (String) itr.next();
			String[] values = (String[]) tmp.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			parms.put(name, valueStr);
		}

		// 返回参数
		String is_trade_success = req.getParameter("trade_status");
		// String sign_type = req.getParameter("sign_type");
		// String sign = req.getParameter("sign");
		String oid = req.getParameter("out_trade_no");
		// String orderTitle = req.getParameter("subject");
		// String buyer_email = req.getParameter("buyer_email");
		// String buyer_id = req.getParameter("buyer_id");
		float price = Float.valueOf(req.getParameter("price"));
		OrderService orderService = (OrderService) getApplicationContext().getBean("orderService");
		NotificationService notificationService = (NotificationService) getApplicationContext()
				.getBean("notificationService");

		// 验证通知的MD5
		if (AlipayNotify.verify(parms)) {
			// 交易成功
			Order order = orderService.queryByShowId(oid, false);
			if (is_trade_success.equals("TRADE_SUCCESS")) {
				// TODO:
				if (order == null) {
					LogUtil.info("order id " + oid + " is not existed", this.getClass());
					returnSuccess(resp);
					return;
				}
				if (!(price == order.getMoney())) {
					LogUtil.error("TRADE_SUCCESS order id:" + oid + ", price is wrong, it should be " + order.getMoney()
							+ ", but it is " + price, this.getClass());
					order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL + "," + order.getState());
					WarnUtil.sendWarnToCTO("order id:" + oid + ", price is wrong, it should be " + order.getMoney()
							+ ", but it is " + price);
					orderService.update(order, false);
					returnSuccess(resp);
					return;
				}
				String state = order.getState().split(",")[0];
				if (!state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
					LogUtil.error("TRADE_SUCCESS order id:" + oid + ", state is wrong", this.getClass());
					WarnUtil.sendWarnToCTO("TRADE_SUCCESS order id:" + oid + ", state is wrong");
					// order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL
					// + "," + order.getState());
					// orderService.update(order);
					returnSuccess(resp);
					return;
				}
				order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
				finishOrder(orderService, order);

				NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
						"尊敬的用户，订单号为" + order.getOrderNo() + "的订单已经付款完成，请等待导师接受订单", order.getCreateUser(),
						notificationService);
				NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
						"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，用户(" + order.getCustomerName() + ")已经付款，等待您的接受。",
						order.getTeacher(), notificationService);
				returnSuccess(resp);
			} else if (is_trade_success.equals("TRADE_FINISHED")) {
				if (order == null) {
					LogUtil.info("order id " + oid + " is not existed", this.getClass());
					returnSuccess(resp);
					return;
				}
				if (!(price == order.getMoney())) {
					LogUtil.error("TRADE_FINISHED order id:" + oid + ", price is wrong, it should be "
							+ order.getMoney() + ", but it is " + price, this.getClass());
					order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL + "," + order.getState());
					WarnUtil.sendWarnToCTO("TRADE_FINISHED order id:" + oid + ", price is wrong, it should be "
							+ order.getMoney() + ", but it is " + price);
					orderService.update(order, false);
					returnSuccess(resp);
					return;
				}
				String state = order.getState().split(",")[0];
				if (!state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
					LogUtil.error("TRADE_FINISHED order id:" + oid + ", state is wrong", this.getClass());
					WarnUtil.sendWarnToCTO("TRADE_FINISHED order id:" + oid + ", state is wrong");
					// order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL
					// + "," + order.getState());
					// orderService.update(order);
					returnSuccess(resp);
					return;
				}
				order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
				finishOrder(orderService, order);

				NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
						"尊敬的用户，订单号为" + order.getOrderNo() + "的订单已经付款完成，请等待导师接受订单", order.getCreateUser(),
						notificationService);
				NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
						"尊敬的导师，订单号为" + order.getOrderNo() + "的订单，用户(" + order.getCustomerName() + ")已经付款，等待您的接受。",
						order.getTeacher(), notificationService);
				returnSuccess(resp);
			}
			// 因为交易超时或者我们主动关闭而导致交易失败,退款
			else if (is_trade_success.equals("TRADE_CLOSED")) {
				// TODO：

				// 订单存在问题
				if (order == null) {
					LogUtil.info("order id: " + oid + ",get notify from ALipay but orderNO is not existed",
							this.getClass());
					returnSuccess(resp);
				}
				String state = order.getState().split(",")[0];
				if (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
					// 用户未支付结果交易超时自动关闭
					order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_END_FAILED + ","
							+ OrderService.ORDER_STATE_CANCEL_PAID + "," + order.getState());
					LogUtil.info("order id: " + oid + ", get notify from Alipay, time up and trade is closed",
							this.getClass());
					orderService.update(order, false);
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的用户，订单号为" + order.getOrderNo() + "的订单已经取消", order.getCreateUser(), notificationService);
				} else if (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_END_FAILED)
						|| state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_CANCEL_PAID)) {
					// 用户生成支付宝订单之后主动取消了订单
					// order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_END_FAILED
					// + "," + order.getState());
					LogUtil.info("order id: " + oid + ", get notify from Alipay, and trade has closed",
							this.getClass());
				} else if (state.equals(OrderService.ORDER_STATE_WAIT_RETURN)) {
					order.setState(OrderService.ORDER_STATE_END_FAILED + "," + OrderService.ORDER_STATE_RETURN_SUCCESS
							+ "," + order.getState());
					orderService.update(order, false);
					LogUtil.info(
							"order id: " + oid
									+ ", get notify from Alipay, return money successfully and trade is closed",
							this.getClass());
					NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
							"尊敬的学员，订单号为" + order.getOrderNo() + "的订单，已经成功退款，请注意查收.如有疑问请咨询lijunlan@1yingli.cn.",
							order.getCreateUser(), notificationService);
				} else {
					order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL + "," + order.getState());
					LogUtil.error("order id:" + oid + ", state is wrong", this.getClass());
					WarnUtil.sendWarnToCTO("TRADE_CLOSED order id:" + oid + ", state is wrong");
				}
				returnSuccess(resp);
			}
		} else {
			resp.setCharacterEncoding("utf-8");
			OutputStream stream = resp.getOutputStream();
			stream.write("fail".getBytes("UTF-8"));
		}
	}

	private void finishOrder(OrderService orderService, Order order) {
		order.setPayMethod(OrderService.ORDER_PAYMETHOD_ALIPAY);
		orderService.updateAndPlusNumber(order);
	}

	private void returnSuccess(HttpServletResponse resp) throws IOException, UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		OutputStream stream = resp.getOutputStream();
		stream.write("success".getBytes("UTF-8"));
	}

}
