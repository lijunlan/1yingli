package cn.yiyingli.Persistant;

import org.apache.poi.ss.formula.functions.Na;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ADDITIONALPAY")
public class AdditionalPay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDITIONALPAY_ID")
	private Long id;

	@ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private Order order;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User user;

	@ManyToOne(targetEntity = OrderList.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ORDERLIST_ID", updatable = false)
	private OrderList orderList;

	@Column(name = "REASON", nullable = false)
	private String reason;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "SALARYSTATE")
	private Short salaryState;

	@Column(name = "PAYMETHOD")
	private Short payMethod;

	@Column(name = "MONEY",nullable = false)
	private Float money;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Short getSalaryState() {
		return salaryState;
	}

	public void setSalaryState(Short salaryState) {
		this.salaryState = salaryState;
	}

	public Short getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Short payMethod) {
		this.payMethod = payMethod;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}
}
