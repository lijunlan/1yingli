package cn.yiyingli.Persistant;

import java.util.HashSet;
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

	@Column(name = "ORIGINPRICE", nullable = false)
	private Float originPrice;

	@Column(name = "PRICE", nullable = false)
	private Float price;

	@Column(name = "QUANTIFIER", nullable = false)
	private String quantifier;

	@Column(name = "NUMERAL", nullable = false)
	private Float numeral;

	@Column(name = "COUNT", nullable = false)
	private Integer count;

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
	/**
	 * 我们自己生成的订单号
	 */
	@Column(name = "ORDERNO", nullable = true, unique = true)
	private String orderNo;

	@Column(name = "QUESTION", nullable = false, length = 500)
	private String question;

	@Column(name = "USERINTRODUCE", nullable = false, length = 500)
	private String userIntroduce;

	@Column(name = "ICONURL", nullable = false)
	private String iconUrl;

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

	/**
	 * 是否回访 面向管理员
	 */
	@Column(name = "RETURNVISIT", nullable = false)
	private Boolean returnVisit;

	/**
	 * 备注 面向管理员
	 */
	@Column(name = "REMARK", nullable = true, length = 500)
	private String remark;

	/**
	 * 支付宝流水号
	 */
	@Column(name = "UNIQUENO", nullable = true)
	private String uniqueNo;

	@Column(name = "REFUSEREASON", nullable = true)
	private String refuseReason;

	@Column(name = "SERVICEID", nullable = true)
	private Long serviceId;

	@Column(name = "SERVICETITLE", nullable = false)
	private String serviceTitle;

	@Column(name = "SERVICESUMMARY", nullable = false)
	private String serviceSummary;

	// 学员选择时间
	@Column(name = "SELECTTIME", nullable = false)
	private String selectTime;

	// 导师确定时间
	@Column(name = "OKTIME", nullable = true)
	private String okTime;

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private Set<Comment> comments = new HashSet<Comment>();

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

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = true)
	private Teacher teacher;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = true)
	private User createUser;

	@ManyToOne(targetEntity = OrderList.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ORDERLIST_ID", updatable = false)
	private OrderList orderList;
	
	@ManyToOne(targetEntity = Distributor.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID", updatable = true)
	private Distributor distributor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getUserIntroduce() {
		return userIntroduce;
	}

	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getOkTime() {
		return okTime;
	}

	public void setOkTime(String okTime) {
		this.okTime = okTime;
	}

	public String getServiceTitle() {
		return serviceTitle;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public String getQuantifier() {
		return quantifier;
	}

	public void setQuantifier(String quantifier) {
		this.quantifier = quantifier;
	}

	public Float getNumeral() {
		return numeral;
	}

	public void setNumeral(Float numeral) {
		this.numeral = numeral;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Short getSalaryState() {
		return salaryState;
	}

	public void setSalaryState(Short salaryState) {
		this.salaryState = salaryState;
	}

	public Float getOriginMoney() {
		return originMoney;
	}

	public void setOriginMoney(Float originMoney) {
		this.originMoney = originMoney;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public Short getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Short payMethod) {
		this.payMethod = payMethod;
	}

	public String getPaypalNo() {
		return paypalNo;
	}

	public void setPaypalNo(String paypalNo) {
		this.paypalNo = paypalNo;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

	public Boolean getReturnVisit() {
		return returnVisit;
	}

	public void setReturnVisit(Boolean returnVisit) {
		this.returnVisit = returnVisit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Float originPrice) {
		this.originPrice = originPrice;
	}

	public String getServiceSummary() {
		return serviceSummary;
	}

	public void setServiceSummary(String serviceSummary) {
		this.serviceSummary = serviceSummary;
	}

}