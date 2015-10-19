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

public class MGetOrderListService extends MsgService {

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
		return getData().containsKey("mid") && getData().containsKey("page") && (getData().containsKey("state")||getData().containsKey("salaryState"));
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		List<Order> orders;
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		if(getData().containsKey("salaryState")){
			short salaryState=Short.parseShort((String) getData().get("salaryState"));
			int page = 0;
			if (getData().get("page").equals("max")) {
				long count = getOrderService().querySumNoBySalaryState(salaryState);
				if (count % 9 > 0)
					page = (int) (count / 9) + 1;
				else
					page = (int) (count / 9);
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
			orders=getOrderService().queryListBySalaryState(salaryState, page);
		}else{
			String state = (String) getData().get("state");
			int page = 0;
			if (getData().get("page").equals("max")) {
				long count = getOrderService().querySumNoByState(state);
				if (count % 9 > 0)
					page = (int) (count / 9) + 1;
				else
					page = (int) (count / 9);
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
			orders = getOrderService().queryListByState(state, page, false);
		}
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			map.put("orderId", o.getOrderNo());
			map.put("createTime", o.getCreateTime());
			map.put("title", o.getServiceTitle());
			map.put("price", o.getMoney());
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
			map.put("okTime", o.getOkTime());
			map.put("weixin", o.getCustomerContact());
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());

	}

}
