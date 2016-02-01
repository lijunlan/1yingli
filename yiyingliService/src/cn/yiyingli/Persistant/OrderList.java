package cn.yiyingli.Persistant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "ORDERLIST")
public class OrderList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDERLIST_ID")
	private Long id;

	@OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDERLIST_ID", updatable = true, insertable = true)
	@IndexColumn(name = "ORDERS_NO", base = 1)
	private List<Order> orders = new ArrayList<Order>();

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "PAYTIME", nullable = true)
	private String payTime;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@Column(name = "ORDERLISTNO", nullable = true, unique = true)
	private String orderListNo;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User user;

	@Column(name = "STATE", nullable = false)
	private String state;

	@Column(name = "ORIGNMONEY", nullable = false)
	private Float originMoney;

	@Column(name = "NOWMONEY", nullable = false)
	private Float nowMoney;

	@Column(name = "PAYMONEY", nullable = false)
	private Float payMoney;

	@Column(name = "CUSTOMERPHONE", nullable = true)
	private String customerPhone;

	@Column(name = "CUSTOMEREMAIL", nullable = true)
	private String customerEmail;

	@Column(name = "CUSTOMERWX", nullable = true)
	private String customerWX;

	@Column(name = "CUSTOMERNAME", nullable = false)
	private String customerName;

	@OneToMany(targetEntity = Voucher.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDERLIST_ID", updatable = true)
	private Set<Voucher> useVouchers = new HashSet<Voucher>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getOrderListNo() {
		return orderListNo;
	}

	public void setOrderListNo(String orderListNo) {
		this.orderListNo = orderListNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setOriginMoney(Float originMoney) {
		this.originMoney = originMoney;
	}

	public Float getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}

	public Set<Voucher> getUseVouchers() {
		return useVouchers;
	}

	public void setUseVouchers(Set<Voucher> useVouchers) {
		this.useVouchers = useVouchers;
	}

	public Float getOriginMoney() {
		return originMoney;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Float getNowMoney() {
		return nowMoney;
	}

	public void setNowMoney(Float nowMoney) {
		this.nowMoney = nowMoney;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerWX() {
		return customerWX;
	}

	public void setCustomerWX(String customerWX) {
		this.customerWX = customerWX;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
