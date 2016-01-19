package cn.yiyingli.Service.Impl;

import java.util.Calendar;
import java.util.List;
import cn.yiyingli.Dao.OrderDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.TimeTaskUtil;
import cn.yiyingli.toPersistant.POrderUtil;

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
		order.setOrderNo(POrderUtil.getOrderNo(id));
		if (order.getDistributor() != null) {
			getOrderDao().updateDistriOrderNumber(order, order.getDistributor());
		} else {
			getOrderDao().update(order);
		}
		TimeTaskUtil.sendTimeTask("change", "order",
				(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 48) + "",
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
	}

	@Override
	public void update(Order order, boolean addMile) {
		if (addMile) {
			if (!order.getOnSale()) {
				Teacher teacher = order.getTeacher();
				float time = order.getTime();
				long m = (long) (10 * time);
				getTeacherDao().update(teacher);
				getTeacherDao().updateAddMile(teacher.getId(), m);
			}
			getOrderDao().updateOrderWhenOrderFinish(order);
		} else {
			getOrderDao().update(order);
		}
		// 如果订单完成，给分销人更新数据
		// if (order.getState().startsWith(ORDER_STATE_WAIT_COMMENT)) {
		// if (!order.getOnSale()) {
		// Teacher teacher = order.getTeacher();
		// float time = order.getTime();
		// long m = (long) (10 * time);
		// teacher.setMile(teacher.getMile() + m);
		// getTeacherDao().update(teacher);
		// }
		// getOrderDao().updateOrderWhenOrderFinish(order);
		// } else {
		// getOrderDao().update(order);
		// }
		// 通知管理员
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
		}
		// else if (order.getState().startsWith(ORDER_STATE_FINISH_PAID)) {
		// TimeTaskUtil.sendTimeTask("change", "order",
		// (Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24) +
		// "",
		// new SuperMap().put("state", order.getState()).put("orderId",
		// order.getOrderNo()).finishByJson());
		// }
	}

	@Override
	public void updateAndPlusNumber(Order order) {
		if (order.getState().startsWith(ORDER_STATE_FINISH_PAID)) {
			order.setPayTime(Calendar.getInstance().getTimeInMillis() + "");
		}
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
	public List<Order> queryListAll(int page) {
		return getOrderDao().queryListAll(page, PAGE_SIZE_INT);
	}

	@Override
	public List<Order> queryListAll(int page, int pageSize) {
		return getOrderDao().queryListAll(page, pageSize);
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
		return getOrderDao().querySumNoBySalaryState(salaryState);
	}

	@Override
	public List<Order> queryListBySalaryState(short salaryState, int page) {
		return getOrderDao().queryListBySalaryState(page, PAGE_SIZE_INT, salaryState);
	}

	@Override
	public List<Order> queryListBySalaryState(short salaryState, int page, String rank) {
		if (rank == null)
			return getOrderDao().queryListBySalaryState(page, PAGE_SIZE_INT, salaryState);
		else if ("D".equals(rank)) {
			return getOrderDao().queryListBySalaryState(salaryState, page, PAGE_SIZE_INT, "DESC");
		} else if ("A".equals(rank)) {
			return getOrderDao().queryListBySalaryState(salaryState, page, PAGE_SIZE_INT, "ASC");
		} else {
			return getOrderDao().queryListBySalaryState(page, PAGE_SIZE_INT, salaryState);
		}
	}

	@Override
	public List<Order> queryListByState(String state, int page, boolean lazy, String rank) {
		if (rank == null)
			return getOrderDao().queryListByState(state, page, PAGE_SIZE_INT, false);
		else if ("D".equals(rank)) {
			return getOrderDao().queryListByState(state, page, PAGE_SIZE_INT, false, "DESC");
		} else if ("A".equals(rank)) {
			return getOrderDao().queryListByState(state, page, PAGE_SIZE_INT, false, "ASC");
		} else {
			return getOrderDao().queryListByState(state, page, PAGE_SIZE_INT, false);
		}
	}

}
