package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExOrderUtil;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderInfoService extends UMsgService {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		super.doit();
		User user = getUser();
		String oid = (String) getData().get("orderId");
		Order o = getOrderService().queryByShowId(oid, false);
		if (o == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42001"));
			return;
		}
		if (o.getCreateUser().getId().longValue() != user.getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsgByCode("44001"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExOrderUtil.assembleOrderToUser(map, o);
		setResMsg(map.finishByJson());
	}

}
