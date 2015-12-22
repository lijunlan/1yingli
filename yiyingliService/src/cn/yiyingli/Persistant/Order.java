package cn.yiyingli.Persistant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long id;

	@Column(name = "ORIGINMONEY", nullable = false)
	private Float originMoney;

	@Column(name = "MONEY", nullable = false)
	private Float money;

	@Column(name = "SALARYSTATE", nullable = false)
	private Short salaryState;

	@Column(name = "CUSTOMERNAME", nullable = false)
	private String customerName;

	@Column(name = "CUSTOMERPHONE", nullable = true)
	private String customerPhone;

	@Column(name = "CUSTOMEREMAIL", nullable = true)
	private String customerEmail;

	@Column(name = "CUSTOMERCONTACT", nullable = true)
	private String customerContact;

	@Column(name = "SERVICECOUNT", nullable = false)
	private Integer serviceCount;

	@Column(name = "SERVICECOMMENTNO", nullable = false)
	private Integer serviceCommentNo;

	/**
	 * 我们自己生成的订单号
	 */
	@Column(name = "ORDERNO", nullable = true, unique = true)
	private String orderNo;

	/**
	 * 用户alipay账号
	 */
	@Column(name = "ALIPAYNO", nullable = true)
	private String alipayNo;

	/**
	 * 用户alipay账号
	 */
	@Column(name = "PAYPALNO", nullable = true)
	private String paypalNo;

	@Column(name = "REFUSEREASON", nullable = true)
	private String refuseReason;

	// 学员选择时间
	@Column(name = "SELECTTIME", nullable = false)
	private String selectTime;

	// 导师确定时间
	@Column(name = "OKTIME", nullable = true)
	private String okTime;

	// 订单生成时间
	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "ONSALE", nullable = false)
	private Boolean onSale;

	@Column(name = "ENDTIME", nullable = true)
	private String endTime;

	@Column(name = "PAYTIME", nullable = true)
	private String payTime;

	@Column(name = "PAYMETHOD", nullable = true)
	private Short payMethod;

	@Column(name = "STATE", nullable = false)
	private String state;

	@Column(name = "USEVOUCHER", nullable = false)
	private Integer useVoucher;

	/**
	 * service的数据，以json储存，不会跟着导师更改而更新
	 */
	@Column(name = "SERVICEINFO", nullable = false, length = 1000)
	private String serviceInfo;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User createUser;

	@ManyToOne(targetEntity = OrderList.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ORDERLIST_ID", updatable = false)
	private OrderList orderList;

	@ManyToOne(targetEntity = Distributor.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID", updatable = false)
	private Distributor distributor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getOriginMoney() {
		return originMoney;
	}

	public void setOriginMoney(Float originMoney) {
		this.originMoney = originMoney;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Short getSalaryState() {
		return salaryState;
	}

	public void setSalaryState(Short salaryState) {
		this.salaryState = salaryState;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public String getPaypalNo() {
		return paypalNo;
	}

	public void setPaypalNo(String paypalNo) {
		this.paypalNo = paypalNo;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}

	public String getOkTime() {
		return okTime;
	}

	public void setOkTime(String okTime) {
		this.okTime = okTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Short getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Short payMethod) {
		this.payMethod = payMethod;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getUseVoucher() {
		return useVoucher;
	}

	public void setUseVoucher(Integer useVoucher) {
		this.useVoucher = useVoucher;
	}

	public String getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Integer getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(Integer serviceCount) {
		this.serviceCount = serviceCount;
	}

	public Integer getServiceCommentNo() {
		return serviceCommentNo;
	}

	public void setServiceCommentNo(Integer serviceCommentNo) {
		this.serviceCommentNo = serviceCommentNo;
	}

}
