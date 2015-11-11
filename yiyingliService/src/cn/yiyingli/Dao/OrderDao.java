package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;

public interface OrderDao {

	void save(Order order);

	Long saveAndReturnId(Order order);

	void remove(Order order);

	void remove(long id);

	void update(Order order);

	Long saveWithUserNumber(Order order, User user);

	void updateFromSql(String sql);

	void updateWithTeacherNumber(Order order, Teacher teacher);

	void updateDistriOrderNumber(Order order, Distributor distributor);

	void updateDistriDealNumberWhenFinished(Order order);

	Order query(long id, boolean lazy);

	Order queryByOrderNo(String orderNo);

	Order queryByShowId(String id, boolean lazy);

	long querySumNo();

	long querySumNoByState(String state);

	long querySumNoByUserId(long userId);

	long querySumNoByTeacherId(long teacherId);

	long querySumNoBySalaryState(short salaryState);

	List<Order> queryListByState(String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTServiceId(long tServiceId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByName(String name, final int page, final int pageSize);

	List<Order> queryListBySalaryState(final int page, final int pageSize, short salaryState);

	List<Order> queryListBySalaryState(short salaryState, int page, int pageSize, String rank);

	List<Order> queryListByState(String state, int page, int pageSize, boolean lazy, String rank);
}
