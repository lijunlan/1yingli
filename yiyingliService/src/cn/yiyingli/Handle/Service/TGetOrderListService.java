package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class TGetOrderListService extends MsgService {

	private OrderService orderService;

	private TeacherService teacherService;

	private UserMarkService userMarkService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("uid") && getData().containsKey("page")
				|| getData().containsKey("state");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Teacher teacher = getTeacherService().queryByUserIdWithTService(user.getId(), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("you are not a teacher"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsg("uid don't match teacherId"));
			return;
		}
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		if (getData().get("page").equals("max")) {
			long count = getOrderService().querySumNoByTeacherId(teacher.getId());
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
			orders = getOrderService().queryListByTeacherId(teacher.getId(), states, page, false);
		} else {
			orders = getOrderService().queryListByTeacherId(teacher.getId(), page, false);
		}
		List<String> sends = new ArrayList<String>();
		for (Order o : orders) {
			SuperMap map = new SuperMap();
			map.put("orderId", o.getOrderNo());
			map.put("createTime", o.getCreateTime());
			map.put("title", o.getServiceTitle());
			map.put("price", o.getMoney());
			map.put("time", o.getTime());
			map.put("iconUrl", o.getCreateUser().getIconUrl());
			map.put("name", o.getCustomerName());
			map.put("state", o.getState());
			map.put("question", o.getQuestion());
			map.put("phone", o.getCustomerPhone());
			map.put("email", o.getCustomerEmail());
			map.put("userIntroduce", o.getUserIntroduce());
			map.put("selectTimes", o.getSelectTime());
			map.put("okTime", o.getOkTime());
			map.put("contact", o.getCustomerContact());
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
	}

}
