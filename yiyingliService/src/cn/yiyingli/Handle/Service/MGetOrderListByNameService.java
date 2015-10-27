package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetOrderListByNameService extends MsgService {

	private ManagerMarkService managerMarkService;

	private OrderService orderService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") && getData().containsKey("name") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		String name = (String) getData().get("name");
		int page = 0;
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
		List<Order> orders = getOrderService().queryListByName(name,page);
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			map.put("orderId", o.getOrderNo());
			map.put("createTime", o.getCreateTime());
			map.put("title", o.getServiceTitle());
			map.put("price", o.getMoney());
			map.put("originPrice", o.getOriginMoney());
			map.put("time", o.getTime());
			map.put("teacherName", o.getTeacher().getName());
			map.put("teacherUrl", o.getTeacher().getIconUrl());
			map.put("teacherAlipayNo", o.getAlipayNo());
			map.put("customerName", o.getCustomerName());
			map.put("customerPhone", o.getCustomerPhone());
			map.put("customerEmail", o.getCustomerEmail());
			map.put("state", o.getState());
			map.put("question", o.getQuestion());
			map.put("userIntroduce", o.getUserIntroduce());
			map.put("selectTimes", o.getSelectTime());
			map.put("salaryState", o.getSalaryState());
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
	}

}
