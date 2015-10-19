package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderInfoService extends MsgService {

	private UserMarkService userMarkService;

	private OrderService orderService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		String oid = (String) getData().get("orderId");
		Order o = getOrderService().queryByShowId(oid, false);
		if (o == null) {
			setResMsg(MsgUtil.getErrorMsg("order is not existed"));
			return;
		}
		if (o.getCreateUser().getId().longValue() != user.getId().longValue()) {
			setResMsg(MsgUtil.getErrorMsg("this order is not belong to you"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		map.put("orderId", o.getOrderNo());
		map.put("createTime", o.getCreateTime());
		map.put("title", o.getServiceTitle());
		map.put("price", o.getMoney());
		map.put("time", o.getTime());
		map.put("teacherId", o.getTeacher().getId());
		map.put("teacherName", o.getTeacher().getName());
		map.put("teacherUrl", o.getTeacher().getIconUrl());
		map.put("state", o.getState());
		map.put("question", o.getQuestion());
		map.put("userIntroduce", o.getUserIntroduce());
		map.put("selectTimes", o.getSelectTime());
		map.put("okTime", o.getOkTime());
		map.put("phone", o.getCustomerPhone());
		map.put("email", o.getCustomerEmail());
		map.put("name", o.getCustomerName());
		map.put("contact", o.getCustomerContact());
		setResMsg(map.finishByJson());
	}

}
