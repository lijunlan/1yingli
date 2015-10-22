package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderListService extends MsgService {

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
		return getData().containsKey("uid") && getData().containsKey("page") || getData().containsKey("state");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		int page = 0;
		long count = user.getOrderNumber();
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", count);
		if (getData().get("page").equals("max")) {
			if (count % OrderService.PAGE_SIZE_INT > 0)
				page = (int) (count / OrderService.PAGE_SIZE_INT) + 1;
			else
				page = (int) (count / OrderService.PAGE_SIZE_INT);
			if (page == 0)
				page = 1;
			toSend.put("page", page);
		} else {
			try {
				page = Integer.parseInt((String) getData().get("page"));
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsg("page is wrong"));
				return;
			}
			if (page <= 0) {
				setResMsg(MsgUtil.getErrorMsg("page is wrong"));
				return;
			}
		}
		List<Order> orders = null;
		if (getData().containsKey("state")) {
			String s = (String) getData().get("state");
			String states[] = s.split("\\|");
			orders = getOrderService().queryListByUserId(user.getId(), states, page, false);
		} else {
			orders = getOrderService().queryListByUserId(user.getId(), page, false);
		}
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
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
			sends.add(map.finishByJson());
		}
		toSend.put("state", "success");
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());

	}

}
