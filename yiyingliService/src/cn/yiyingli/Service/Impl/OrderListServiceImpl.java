package cn.yiyingli.Service.Impl;

import java.util.Calendar;
import java.util.List;
import cn.yiyingli.Dao.OrderListDao;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.OrderList;
import cn.yiyingli.Service.OrderListService;
import cn.yiyingli.Util.TimeTaskUtil;

public class OrderListServiceImpl implements OrderListService {

	private OrderListDao orderListDao;

	public OrderListDao getOrderListDao() {
		return orderListDao;
	}

	public void setOrderListDao(OrderListDao orderListDao) {
		this.orderListDao = orderListDao;
	}

	@Override
	public String saveAndSubCount(OrderList orderList) {
		boolean result = getOrderListDao().queryCheckServiceNumber(orderList);
		if (!result) {
			return ORDER_ERROR_COUNT_LIMITED;
		}
		getOrderListDao().updateServiceProNumber(orderList);
		long id = getOrderListDao().saveAndReturnId(orderList);
		orderList.setOrderListNo("" + Calendar.getInstance().get(Calendar.YEAR)
				+ String.valueOf((Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1000)).substring(1)
				+ (600000000L + id));
		for (Order order : orderList.getOrders()) {
			order.setOrderNo("" + Calendar.getInstance().get(Calendar.YEAR)
					+ String.valueOf((Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1000)).substring(1)
					+ (100000000L + order.getId()));
		}
		getOrderListDao().update(orderList);
		TimeTaskUtil.sendTimeTask("change", "orderList",
				(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 48) + "",
				new SuperMap().put("state", orderList.getState()).put("orderListId", orderList.getOrderListNo())
						.finishByJson());
		return orderList.getOrderListNo();
	}

	@Override
	public void updateAndAddCount(OrderList orderList) {
		getOrderListDao().updateAndAddCount(orderList);
	}

	@Override
	public Long saveAndReturnId(OrderList orderList) {
		return getOrderListDao().saveAndReturnId(orderList);
	}

	@Override
	public void remove(OrderList orderList) {
		getOrderListDao().remove(orderList);
	}

	@Override
	public void remove(long id) {
		getOrderListDao().remove(id);
	}

	@Override
	public void update(OrderList orderList) {
		getOrderListDao().update(orderList);
	}

	@Override
	public void updateAndPlusNumber(OrderList orderList) {
		getOrderListDao().updateWithTeacherNumber(orderList, orderList.getTeacher().getId());
	}

	@Override
	public OrderList queryByOrderListNo(String orderListNo) {
		return getOrderListDao().queryByOrderListNo(orderListNo);
	}

	@Override
	public OrderList query(long id) {
		return getOrderListDao().query(id);
	}

	@Override
	public List<OrderList> queryListByTeacher(long teacherId, int page, int pageSize) {
		return getOrderListDao().queryListByTeacher(teacherId, page, pageSize);
	}

	@Override
	public List<OrderList> queryListByUser(long userId, int page, int pageSize) {
		return getOrderListDao().queryListByUser(userId, page, pageSize);
	}

	@Override
	public List<OrderList> queryListByTeacher(long teacherId, int page) {
		return getOrderListDao().queryListByTeacher(teacherId, page, PAGE_SIZE_INT);
	}

	@Override
	public List<OrderList> queryListByUser(long userId, int page) {
		return getOrderListDao().queryListByUser(userId, page, PAGE_SIZE_INT);
	}

}
