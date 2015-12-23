package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.OrderList;

public interface OrderListDao {

	void save(OrderList orderList);

	Long saveAndReturnId(OrderList orderList);

	void remove(OrderList orderList);

	void remove(long id);

	void update(OrderList orderList);

	void updateWithTeacherNumber(OrderList orderList, long teacherId);

	OrderList queryByOrderListNo(String orderListNo);

	OrderList query(long id);

	List<OrderList> queryListByTeacher(long teacherId, int page, int pageSize);

	List<OrderList> queryListByUser(long userId, int page, int pageSize);

}
