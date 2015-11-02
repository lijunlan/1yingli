package cn.yiyingli.Service.Impl;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import cn.yiyingli.Dao.OrderDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.TimeTaskUtil;

public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;

	private UserDao userDao;

	private TeacherDao teacherDao;

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Override
	public String save(Order order) {
		long id = getOrderDao().saveWithUserNumber(order, order.getCreateUser());
		order.setOrderNo("" + Calendar.getInstance().get(Calendar.YEAR) + new Random().nextInt(10)
				+ new Random().nextInt(10) + new Random().nextInt(10) + (100000000L + id));
		getOrderDao().update(order);
		TimeTaskUtil.sendTimeTask("change", "order",
				(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) + "",
				new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		return order.getOrderNo();
	}

	@Override
	public Long saveAndReturnId(Order order) {
		return getOrderDao().saveAndReturnId(order);
	}

	@Override
	public void remove(Order order) {
		getOrderDao().remove(order);
	}

	@Override
	public void remove(long id) {
		getOrderDao().remove(id);
	}

	@Override
	public void update(Order order) {
		getOrderDao().update(order);
		if (order.getState().startsWith(ORDER_STATE_WAIT_RETURN)) {
			NotifyUtil.notifyManager(new SuperMap().put("type", "withdraw").finishByJson());
		} else if (order.getState().startsWith(ORDER_STATE_MANAGER_IN)) {
			NotifyUtil.notifyManager(new SuperMap().put("type", "managerIn").finishByJson());
		} else if (order.getSalaryState().shortValue() == OrderService.ORDER_SALARY_STATE_NEED) {
			NotifyUtil.notifyManager(new SuperMap().put("type", "salary").finishByJson());
		}
	}

	@Override
	public void updateAndSendTimeTask(Order order) {
		getOrderDao().update(order);
		if (order.getState().startsWith(ORDER_STATE_TEACHER_ACCEPT)) {
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) + "",
					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		} else if (order.getState().startsWith(ORDER_STATE_WAIT_SERVICE)) {
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24 * 14) + "",
					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		} else if (order.getState().startsWith(ORDER_STATE_USER_DISLIKE)) {
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24 * 5) + "",
					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		} else if (order.getState().startsWith(ORDER_STATE_USER_REGRET)) {
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24 * 5) + "",
					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		}else if(order.getState().startsWith(ORDER_STATE_FINISH_PAID)){
			TimeTaskUtil.sendTimeTask("change", "order",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) + "",
					new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
		}
	}

	@Override
	public void updateAndPlusNumber(Order order) {
		getOrderDao().updateWithTeacherNumber(order, order.getTeacher());
		if (order.getState().startsWith(ORDER_STATE_FINISH_PAID)) {
			NotifyUtil.notifyManager(new SuperMap().put("type", "waitConfirm").finishByJson());
		}
		TimeTaskUtil.sendTimeTask("change", "order",
				(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) + "",
				new SuperMap().put("state", order.getState()).put("orderId", order.getOrderNo()).finishByJson());
	}

	@Override
	public Order query(long id, boolean lazy) {
		return getOrderDao().query(id, lazy);
	}

	@Override
	public Order queryByOrderNo(String orderNo) {
		return getOrderDao().queryByOrderNo(orderNo);
	}

	@Override
	public Order queryByShowId(String id, boolean lazy) {
		return getOrderDao().queryByShowId(id, lazy);
	}

	@Override
	public List<Order> queryListByTeacherId(long teacherId, String state, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByTeacherId(teacherId, state, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByTeacherId(long teacherId, String[] state, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByTeacherId(teacherId, state, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByTeacherId(long teacherId, String[] state, int page, boolean lazy) {
		return getOrderDao().queryListByTeacherId(teacherId, state, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByUserId(long userId, String state, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByUserId(userId, state, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByUserId(long userId, String[] state, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByUserId(userId, state, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByUserId(long userId, String[] state, int page, boolean lazy) {
		return getOrderDao().queryListByUserId(userId, state, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByTServiceId(long tServiceId, String state, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByTServiceId(tServiceId, state, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByTeacherId(long teacherId, String state, int page, boolean lazy) {
		return queryListByTeacherId(teacherId, state, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByUserId(long userId, String state, int page, boolean lazy) {
		return queryListByUserId(userId, state, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByTServiceId(long tServiceId, String state, int page, boolean lazy) {
		return queryListByTServiceId(tServiceId, state, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByTeacherId(long teacherId, int page, boolean lazy) {
		return queryListByTeacherId(teacherId, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByTeacherId(long teacherId, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByTeacherId(teacherId, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByUserId(long userId, int page, boolean lazy) {
		return queryListByUserId(userId, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByUserId(long userId, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByUserId(userId, page, pageSize, lazy);
	}

	@Override
	public long querySumNo() {
		return getOrderDao().querySumNo();
	}

	@Override
	public long querySumNoByUserId(long userId) {
		return getOrderDao().querySumNoByUserId(userId);
	}

	@Override
	public long querySumNoByTeacherId(long teacherId) {
		return getOrderDao().querySumNoByTeacherId(teacherId);
	}

	@Override
	public long querySumNoByState(String state) {
		return getOrderDao().querySumNoByState(state);
	}

	@Override
	public List<Order> queryListByState(String state, int page, boolean lazy) {
		return getOrderDao().queryListByState(state, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Order> queryListByState(String state, int page, int pageSize, boolean lazy) {
		return getOrderDao().queryListByState(state, page, pageSize, lazy);
	}

	@Override
	public List<Order> queryListByName(String name, final int page) {
		return getOrderDao().queryListByName(name, page, PAGE_SIZE_INT);
	}

	@Override
	public long querySumNoBySalaryState(short salaryState) {
		// TODO Auto-generated method stub
		return getOrderDao().querySumNoBySalaryState(salaryState);
	}

	@Override
	public List<Order> queryListBySalaryState(short salaryState, int page) {
		// TODO Auto-generated method stub
		return getOrderDao().queryListBySalaryState(page, PAGE_SIZE_INT, salaryState);
	}

}
