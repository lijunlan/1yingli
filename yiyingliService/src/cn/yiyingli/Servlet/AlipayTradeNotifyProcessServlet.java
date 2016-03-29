package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.Persistant.ServicePro;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yiyingli.Alipay.AlipayNotify;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.ExRewardForPay;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.WarnUtil;

/**
 * 支付宝交易通知
 * 
 * @author sdll18
 *
 */
@SuppressWarnings("serial")
public class AlipayTradeNotifyProcessServlet extends HttpServlet {

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
		String extra_common_param = req.getParameter("extra_common_param");
		// String sign_type = req.getParameter("sign_type");
		// String sign = req.getParameter("sign");
		String olid = req.getParameter("out_trade_no");
		// String orderTitle = req.getParameter("subject");
		// String buyer_email = req.getParameter("buyer_email");
		// String buyer_id = req.getParameter("buyer_id");
		float price = Float.valueOf(req.getParameter("price"));
		OrderListService orderListService = (OrderListService) getApplicationContext().getBean("orderListService");
		NotificationService notificationService = (NotificationService) getApplicationContext()
				.getBean("notificationService");

		// 验证通知的MD5
		if (AlipayNotify.verify(parms)) {
			if (extra_common_param != null) {
				RewardService rewardService = (RewardService) getApplicationContext().getBean("rewardService");
				ExRewardForPay.dealReward(rewardService, extra_common_param, olid);
				return;
			}
			// 交易成功
			OrderList orderList = orderListService.queryByOrderListNo(olid);
			if (orderList == null) {
				LogUtil.info("orderList id " + olid + " is not existed", this.getClass());
				returnSuccess(resp);
				return;
			}
			String state = orderList.getState().split(",")[0];
			// 交易成功
			if (is_trade_success.equals("TRADE_SUCCESS")) {
				if (!(price == orderList.getPayMoney().floatValue())) {
					LogUtil.error("TRADE_SUCCESS orderList id:" + olid + ", price is wrong, it should be "
							+ orderList.getPayMoney() + ", but it is " + price, this.getClass());
					orderList.setState(OrderListService.ORDER_STATE_ABNORMAL + "," + orderList.getState());
					WarnUtil.sendWarnToCTO("orderList id:" + olid + ", price is wrong, it should be "
							+ orderList.getPayMoney() + ", but it is " + price);
					// orderService.update(order, false);
					returnSuccess(resp);
					return;
				}
				if (!OrderListService.ORDER_STATE_NOT_PAID.equals(state)) {
					LogUtil.error("TRADE_SUCCESS orderList id:" + olid + ", state error", this.getClass());
					WarnUtil.sendWarnToCTO("TRADE_SUCCESS orderList id:" + olid + ", has finished the payment before");
					orderList.setState(
							cn.yiyingli.Service.OrderListService.ORDER_STATE_ABNORMAL + "," + orderList.getState());
					orderListService.update(orderList);

					returnSuccess(resp);
					return;
				}
				// 完成订单
				finishOrder(orderListService, orderList, notificationService);
				returnSuccess(resp);
			} else if (is_trade_success.equals("TRADE_FINISHED")) {
				if (!(price == orderList.getPayMoney().floatValue())) {
					LogUtil.error("TRADE_FINISHED orderList id:" + olid + ", price is wrong, it should be "
							+ orderList.getPayMoney() + ", but it is " + price, this.getClass());
					WarnUtil.sendWarnToCTO("TRADE_FINISHED orderList id:" + olid + ", price is wrong, it should be "
							+ orderList.getPayMoney() + ", but it is " + price);
					returnSuccess(resp);
					return;
				}
				// if (OrderListService.ORDER_STATE_FINISH_PAID.equals(state)) {
				// LogUtil.error("TRADE_FINISHED order id:" + olid + ", state is
				// wrong", this.getClass());
				// WarnUtil.sendWarnToCTO("TRADE_FINISHED order id:" + olid + ",
				// state is wrong");
				// returnSuccess(resp);
				// return;
				// }
				// finishOrder(orderListService, orderList,
				// notificationService);
				returnSuccess(resp);
			}
			// 因为交易超时或者我们主动关闭而导致交易失败,退款
			else if (is_trade_success.equals("TRADE_CLOSED")) {
				// TODO 现在订单包含在流水号里面。所以不能直接退款了 ，所以此项去掉
				// if (!orderList.getFinishPay()) {
				// // 用户未支付结果交易超时自动关闭
				// //
				// order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_END_FAILED
				// // + ","
				// // + OrderService.ORDER_STATE_CANCEL_PAID + "," +
				// // order.getState());
				// LogUtil.info("order id: " + oid + ", get notify from Alipay,
				// time up and trade is closed",
				// this.getClass());
				// // orderService.update(order, false);
				// // NotifyUtil.notifyUserOrder(order.getCustomerPhone(),
				// // order.getCustomerEmail(),
				// // "尊敬的用户，订单号为" + order.getOrderNo() + "的订单已经取消",
				// // order.getCreateUser(), notificationService);
				// } else if
				// (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_END_FAILED)
				// ||
				// state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_CANCEL_PAID))
				// {
				// // 用户生成支付宝订单之后主动取消了订单
				// //
				// order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_END_FAILED
				// // + "," + order.getState());
				// LogUtil.info("order id: " + oid + ", get notify from Alipay,
				// and trade has closed",
				// this.getClass());
				// } else if
				// (state.equals(OrderService.ORDER_STATE_WAIT_RETURN)) {
				// order.setState(OrderService.ORDER_STATE_END_FAILED + "," +
				// OrderService.ORDER_STATE_RETURN_SUCCESS
				// + "," + order.getState());
				// orderService.update(order, false);
				// LogUtil.info(
				// "order id: " + oid
				// + ", get notify from Alipay, return money successfully and
				// trade is closed",
				// this.getClass());
				// NotifyUtil.notifyUserOrder(order.getCustomerPhone(),
				// order.getCustomerEmail(),
				// "尊敬的学员，订单号为" + order.getOrderNo() +
				// "的订单，已经成功退款，请注意查收.如有疑问请咨询lijunlan@1yingli.cn.",
				// order.getCreateUser(), notificationService);
				// } else {
				// order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_ABNORMAL
				// + "," + order.getState());
				// LogUtil.error("order id:" + oid + ", state is wrong",
				// this.getClass());
				// WarnUtil.sendWarnToCTO("TRADE_CLOSED order id:" + oid + ",
				// state is wrong");
				// }
				returnSuccess(resp);
			}
		} else {
			resp.setCharacterEncoding("utf-8");
			OutputStream stream = resp.getOutputStream();
			stream.write("fail".getBytes("UTF-8"));
		}
	}

	private void finishOrder(OrderListService orderListService, OrderList orderList,
			NotificationService notificationService) {
		String time = Calendar.getInstance().getTimeInMillis() + "";
		for (Order order : orderList.getOrders()) {
			if(order.getServiceType().equals(ServicePro.SERVICE_TYPE_BARGAIN)) {
				order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_WAIT_SERVICE + "," + order.getState());
			} else {
				order.setState(cn.yiyingli.Service.OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
			}
			order.setPayTime(time);
			order.setPayMethod(OrderService.ORDER_PAYMETHOD_ALIPAY);
			NotifyUtil.notifyManager(new SuperMap().put("type", "waitConfirm").finishByJson());
//			TimeTaskUtil.sendTimeTask("change", "order",
//					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) + "",
//					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		}
		orderList.setState(OrderListService.ORDER_STATE_FINISH_PAID + "," + orderList.getState());
		orderListService.updateAndPlusNumber(orderList);
		NotifyUtil.notifyPayUser(orderList,notificationService);
		NotifyUtil.notifyPayTeacher(orderList,notificationService);
//		NotifyUtil.notifyUserOrder(orderList, "尊敬的用户，流水号为" + orderList.getOrderListNo() + "的订单组已经付款完成，请等待导师接受订单",
//				orderList.getUser(), notificationService);
//		NotifyUtil.notifyTeacher(orderList,
//				"尊敬的导师，流水号为" + orderList.getOrderListNo() + "的订单组，用户已经付款，等待您的接受。",
//				notificationService);
	}

	private void returnSuccess(HttpServletResponse resp) throws IOException, UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		OutputStream stream = resp.getOutputStream();
		stream.write("success".getBytes("UTF-8"));
	}

}
