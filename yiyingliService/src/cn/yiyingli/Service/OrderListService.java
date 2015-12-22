package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.OrderList;

public interface OrderListService {

	public static final int PAGE_SIZE_INT = 12;

	String save(OrderList orderList);

	Long saveAndReturnId(OrderList orderList);

	void remove(OrderList orderList);

	void remove(long id);

	void update(OrderList orderList);

	OrderList queryByOrderListNo(String orderListNo);

	OrderList query(long id);

	List<OrderList> queryListByTeacher(long teacherId, int page, int pageSize);

	List<OrderList> queryListByUser(long userId, int page, int pageSize);

	List<OrderList> queryListByTeacher(long teacherId, int page);

	List<OrderList> queryListByUser(long userId, int page);
}
