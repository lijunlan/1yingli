package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.Persistant.AdditionalPay;
import cn.yiyingli.Persistant.ServicePro;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
				String extra_param = WXMessageUtil.getContent(receive, "attach");
				String olid = WXMessageUtil.getContent(receive, "out_trade_no");
				if (!"none".equals(extra_param)) {
					RewardService rewardService = (RewardService) getApplicationContext().getBean("rewardService");
					ExRewardForPay.dealReward(rewardService, extra_param, olid);
				} else {
					String money = WXMessageUtil.getContent(receive, "total_fee");
					OrderListService orderListService = (OrderListService) getApplicationContext()
							.getBean("orderListService");
					NotificationService notificationService = (NotificationService) getApplicationContext()
							.getBean("notificationService");
					OrderList orderList = orderListService.queryByOrderListNo(olid);
					if (orderList != null) {
						if (Float.valueOf(money) / 100F == Math.round(orderList.getPayMoney()*100)/100F) {
							String state = orderList.getState().split(",")[0];
							if (state.equals(cn.yiyingli.Service.OrderService.ORDER_STATE_NOT_PAID)) {
								finishOrder(orderListService, orderList, notificationService);

							} else {
								LogUtil.error("TRADE_SUCCESS orderList id:" + olid + ", state error", this.getClass());
								WarnUtil.sendWarnToCTO(
										"TRADE_SUCCESS orderList id:" + olid + ", has finished the payment before");
							}
						} else {
							LogUtil.error(
									"TRADE_SUCCESS orderList id:" + olid + ", price is wrong, it should be "
											+ orderList.getPayMoney() + ", but it is " + Float.valueOf(money) / 100F,
									this.getClass());
							orderList.setState(OrderListService.ORDER_STATE_ABNORMAL + "," + orderList.getState());
							WarnUtil.sendWarnToCTO("orderList id:" + olid + ", price is wrong, it should be "
									+ orderList.getPayMoney() + ", but it is " + Float.valueOf(money) / 100F);
							orderListService.update(orderList);
						}
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
			order.setPayMethod(OrderService.ORDER_PAYMETHOD_WEIXIN);
			NotifyUtil.notifyManager(new SuperMap().put("type", "waitConfirm").finishByJson());
			NotifyUtil.notifyTeacher(order, notificationService);
			// TimeTaskUtil.sendTimeTask("change", "order",
			// (Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24)
			// + "",
			// new SuperMap().put("state", order.getState()).put("orderId",
			// order.getOrderNo()).finishByJson());
		}
		OrderService orderService = (OrderService) getApplicationContext()
				.getBean("orderService");
		for (AdditionalPay additionalPay: orderList.getAdditionalPays()) {
			Order order = additionalPay.getOrder();
			additionalPay.setPayMethod(OrderService.ORDER_PAYMETHOD_WEIXIN);
			additionalPay.setState(AdditionalPay.ADDITIONALPAY_STATE_PAID);
			additionalPay.setPayTime(time);
			order.setAdditionalMoney(order.getAdditionalMoney() + additionalPay.getMoney());
			orderService.save(order);
		}
		orderList.setState(OrderListService.ORDER_STATE_FINISH_PAID + "," + orderList.getState());
		orderListService.updateAndPlusNumber(orderList);
//		NotifyUtil.notifyPayUser(orderList,notificationService);
//		NotifyUtil.notifyPayTeacher(orderList,notificationService);
//		NotifyUtil.notifyUserOrder(orderList, "尊敬的用户，流水号为" + orderList.getOrderListNo() + "的订单组已经付款完成，请等待导师接受订单",
//				orderList.getUser(), notificationService);
//		NotifyUtil.notifyTeacher(orderList,
//				"尊敬的导师，流水号为" + orderList.getOrderListNo() + "的订单组，用户已经付款，等待您的接受。",
//				notificationService);
		// NotifyUtil.notifyUserOrder(orderList, "尊敬的用户，流水号为" +
		// orderList.getOrderListNo() + "的订单组已经付款完成，请等待导师接受订单",
		// orderList.getUser(), notificationService);
	}
}
