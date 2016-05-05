package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.User;

public interface OrderDao {

	void save(Order order);

	Long saveAndReturnId(Order order);

	void remove(Order order);

	void remove(long id);

	void update(Order order);

	Long saveWithUserNumber(Order order, User user);

	void updateFromSql(String sql);

	void updateWithTeacherNumber(Order order, long teacherId);

	void updateDistriOrderNumber(Order order, Distributor distributor);

	void updateAndAddCount(Order order);

	/**
	 * 操作包括更新分销人订单数据，并且更新订单状态
	 * 
	 * @param order
	 */
	void updateOrderWhenOrderFinish(Order order);

	Order query(long id, boolean lazy);

	Order queryByOrderNo(String orderNo);

	Order queryByShowId(String id, boolean lazy);

	long querySumNo();

	long querySumNoByState(String state);

	long querySumNoByUserId(long userId);

	long querySumNoByUserIdAndStates(long userId, String[] states);

	long querySumNoByTeacherId(long teacherId);

	long querySumNoByTeacherIdAndStates(long teacherId,String[] states);

	long querySumNoByTeacherIdAndServicePro(long teacherId, long serviceProId, String[] states);

	long querySumNoBySalaryState(short salaryState);

	List<Order> queryListAll(int page, int pageSize);

	List<Order> queryListByOrderNos(long[] orderNos);

	List<Order> queryListByState(String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherIdAndServiceProId(long teacherId, long serviceProId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByName(String name, final int page, final int pageSize);

	List<Order> queryListBySalaryState(final int page, final int pageSize, short salaryState);

	List<Order> queryListBySalaryState(short salaryState, int page, int pageSize, String rank);

	List<Order> queryListByState(String state, int page, int pageSize, boolean lazy, String rank);
}
