package cn.yiyingli.Handle.Service;

import java.io.IOException;

import com.paypal.base.rest.PayPalRESTException;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.PayPal.PayPalMobileConfirm;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class FCheckPaypalPaymentService extends MsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("orderId") && getData().containsKey("paymentId");
	}

	@Override
	public void doit() {
		String oid = (String) getData().get("orderId");
		Order order = getOrderService().queryByShowId(oid, false);
		if (order == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		String state = order.getState().split(",")[0];
		if (!OrderService.ORDER_STATE_NOT_PAID.equals(state)) {
			setResMsg(MsgUtil.getErrorMsgByCode("44002"));
			return;
		}
		String paymentId = (String) getData().get("paymentId");
		try {
			PayPalMobileConfirm payPalMobileConfirm = PayPalMobileConfirm.getInstance();
			if (payPalMobileConfirm.checkPayment(paymentId, order)) {
				order.setState(OrderService.ORDER_STATE_FINISH_PAID + "," + order.getState());
				getOrderService().update(order);
				setResMsg(MsgUtil.getSuccessMsg("check payment successfully"));
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("45004"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("40001"));
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("40002"));
		}
	}

}
