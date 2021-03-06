package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Order;

/**
 * @author sdll18
 *
 */
public interface OrderService {

	public static final short ORDER_PAYMETHOD_ALIPAY = 0;

	public static final short ORDER_PAYMETHOD_PAYPAL = 1;

	public static final short ORDER_PAYMETHOD_WEIXIN = 2;

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
	 * 等待导师确认时间
	 */
	public static final String ORDER_STATE_WAIT_ENSURETIME = "0400";

	/**
	 * 等待服务
	 */
	public static final String ORDER_STATE_WAIT_SERVICE = "0500";

	/**
	 * 服务完毕，用户不满
	 */
	public static final String ORDER_STATE_USER_DISLIKE = "0620";

	/**
	 * 等待退款
	 */
	public static final String ORDER_STATE_WAIT_RETURN = "0700";

	/**
	 * 退款成功
	 */
	public static final String ORDER_STATE_RETURN_SUCCESS = "0800";

	/**
	 * 导师确认服务完毕
	 */
	public static final String ORDER_STATE_SERVICE_FINISH = "0900";

	/**
	 * 等待双方评价
	 */
	public static final String ORDER_STATE_WAIT_COMMENT = "1000";

	/**
	 * 等待导师评价
	 */
	public static final String ORDER_STATE_WAIT_TCOMMENT = "1010";

	/**
	 * 订单成功完成
	 */
	public static final String ORDER_STATE_END_SUCCESS = "1100";

	/**
	 * 订单失败关闭
	 */
	public static final String ORDER_STATE_END_FAILED = "1200";

	/**
	 * 客服介入中
	 */
	public static final String ORDER_STATE_MANAGER_IN = "1300";

	/**
	 * 尚未服务，用户请求退款
	 */
	public static final String ORDER_STATE_USER_REGRET = "1500";

	/**
	 * 订单异常：钱到了，但是订单状态不正确(或者钱到了，跟订单中的钱数目不一致)
	 */
	public static final String ORDER_STATE_ABNORMAL = "1400";

	/**
	 * 订单不需要为导师支付酬劳
	 */
	public static final short ORDER_SALARY_STATE_OFF = 0;

	/**
	 * 订单需要为导师支付酬劳并且尚未支付
	 */
	public static final short ORDER_SALARY_STATE_NEED = 1;

	/**
	 * 订单需要为导师支付酬劳并且完成支付
	 */
	public static final short ORDER_SALARY_STATE_DONE = 2;

	public static final int PAGE_SIZE_INT = 12;

	String save(Order order);

	Long saveAndReturnId(Order order);

	void remove(Order order);

	void remove(long id);

	void update(Order order);

	void update(Order order, boolean addMile);

	void updateAndSendTimeTask(Order order);

	void updateAndPlusNumber(Order order);

	Order query(long id, boolean lazy);

	Order queryByOrderNo(String orderNo);

	Order queryByShowId(String id, boolean lazy);

	long querySumNo();

	long querySumNoByState(String state);

	long querySumNoByUserId(long userId);

	long querySumNoByTeacherId(long teacherId);

	long querySumNoBySalaryState(short salaryState);

	List<Order> queryListAll(int page);

	List<Order> queryListAll(int page, int pageSize);

	List<Order> queryListByState(String state, int page, boolean lazy);

	List<Order> queryListByState(String state, int page, boolean lazy, String rank);

	List<Order> queryListByState(String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, int page, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String state, int page, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByTeacherId(long teacherId, String[] state, int page, boolean lazy);

	List<Order> queryListByUserId(long userId, int page, boolean lazy);

	List<Order> queryListByUserId(long userId, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String[] state, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String[] state, int page, boolean lazy);

	List<Order> queryListByUserId(long userId, String state, int page, int pageSize, boolean lazy);

	List<Order> queryListByUserId(long userId, String state, int page, boolean lazy);

	List<Order> queryListByName(String name, final int page);

	List<Order> queryListBySalaryState(short salaryState, final int page);

	List<Order> queryListBySalaryState(short salaryState, final int page, String rank);
}
