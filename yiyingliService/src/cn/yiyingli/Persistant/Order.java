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

	@Column(name = "MONEY", nullable = false)
	private Float money;

	@Column(name = "TIME", nullable = false)
	private Float time;

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

	/**
	 * 用户alipay账号
	 */
	@Column(name = "ALIPAYNO", nullable = true)
	private String alipayNo;

	/**
	 * 支付宝流水号
	 */
	@Column(name = "UNIQUENO", nullable = true)
	private String uniqueNo;

	@Column(name = "REFUSEREASON", nullable = true)
	private String refuseReason;

	@Column(name = "SERVICETITLE", nullable = false)
	private String serviceTitle;

	@Column(name = "SELECTTIME", nullable = false)
	private String selectTime;

	@Column(name = "OKTIME", nullable = true)
	private String okTime;

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private Set<Comment> comments = new HashSet<Comment>();

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "ENDTIME", nullable = true)
	private String endTime;

	@ManyToOne(targetEntity = TService.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TSERVICE_ID", updatable = false)
	private TService tService;

	@Column(name = "STATE", nullable = false)
	private String state;

	@OneToMany(targetEntity = Voucher.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private Set<Voucher> useVouchers = new HashSet<Voucher>();

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User createUser;

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

	public TService gettService() {
		return tService;
	}

	public void settService(TService tService) {
		this.tService = tService;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Voucher> getUseVouchers() {
		return useVouchers;
	}

	public void setUseVouchers(Set<Voucher> useVouchers) {
		this.useVouchers = useVouchers;
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

	public Float getTime() {
		return time;
	}

	public void setTime(Float time) {
		this.time = time;
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

}
