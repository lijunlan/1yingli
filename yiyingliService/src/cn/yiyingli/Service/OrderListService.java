package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.OrderList;

public interface OrderListService {

	public static final int PAGE_SIZE_INT = 12;

	/**
	 * 客户下单，尚未支付
	 */
	public static final String ORDER_STATE_NOT_PAID = "0100";

	/**
	 * 客户放弃支付，包括：客户取消订单，客户超时未支付
	 */
	public static final String ORDER_STATE_CANCEL_PAID = "0200";

	/**
	 * 客户完成支付，等待导师确认
	 */
	public static final String ORDER_STATE_FINISH_PAID = "0300";

	/**
	 * 订单异常：钱到了，但是订单状态不正确(或者钱到了，跟订单中的钱数目不一致)
	 */
	public static final String ORDER_STATE_ABNORMAL = "0400";

	String save(OrderList orderList);

	Long saveAndReturnId(OrderList orderList);

	void remove(OrderList orderList);

	void remove(long id);

	void update(OrderList orderList);

	void updateAndPlusNumber(OrderList orderList);

	OrderList queryByOrderListNo(String orderListNo);

	OrderList query(long id);

	List<OrderList> queryListByTeacher(long teacherId, int page, int pageSize);

	List<OrderList> queryListByUser(long userId, int page, int pageSize);

	List<OrderList> queryListByTeacher(long teacherId, int page);

	List<OrderList> queryListByUser(long userId, int page);
}
