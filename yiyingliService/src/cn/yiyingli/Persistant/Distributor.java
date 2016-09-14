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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DISTRIBUTOR")
public class Distributor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DISTRIBUTOR_ID")
	private Long id;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "LASTLOGINTIME", nullable = true)
	private String lastLoginTime;

	@Column(name = "USERNAME", nullable = false)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "NUMBER", nullable = false)
	private String number;

	@Column(name = "REGISTERNUMBER", nullable = false)
	private Long registerNumber;

	@Column(name = "DEALNUMBER", nullable = false)
	private Long dealNumber;

	@Column(name = "ORDERNUMBER", nullable = false)
	private Long orderNumber;

	@Column(name = "VOUCHERMONEY", nullable = false)
	private Float voucherMoney;

	@Column(name = "SENDVOUCHER", nullable = false)
	private Boolean sendVoucher;

	@Column(name = "VOUCHERCOUNT", nullable = false)
	private Integer voucherCount;

	@OneToOne(mappedBy = "distributor", fetch = FetchType.LAZY)
	private DistributorMark distributorMark;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID", updatable = false)
	private Set<User> users = new HashSet<User>();

	@OneToMany(targetEntity = Order.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID", updatable = false)
	private Set<Order> orders = new HashSet<Order>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(Long registerNumber) {
		this.registerNumber = registerNumber;
	}

	public Long getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(Long dealNumber) {
		this.dealNumber = dealNumber;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Float getVoucherMoney() {
		return voucherMoney;
	}

	public void setVoucherMoney(Float voucherMoney) {
		this.voucherMoney = voucherMoney;
	}

	public Boolean getSendVoucher() {
		return sendVoucher;
	}

	public void setSendVoucher(Boolean sendVoucher) {
		this.sendVoucher = sendVoucher;
	}

	public Integer getVoucherCount() {
		return voucherCount;
	}

	public void setVoucherCount(Integer voucherCount) {
		this.voucherCount = voucherCount;
	}

	public DistributorMark getDistributorMark() {
		return distributorMark;
	}

	public void setDistributorMark(DistributorMark distributorMark) {
		this.distributorMark = distributorMark;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}
