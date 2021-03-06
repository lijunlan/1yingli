package cn.yiyingli.Persistant;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "VOUCHER")
public class Voucher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VOUCHER_ID")
	private Long id;

	@Column(name = "MONEY", nullable = false)
	private Float money;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "ENDTIME", nullable = false)
	private String endTime;

	@Column(name = "STARTTIME", nullable = false)
	private String startTime;

	@Column(name = "ORIGIN", nullable = false)
	private String origin;

	@Column(name = "USED", nullable = false)
	private Boolean used;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User ownUser;

	@Column(name = "SERVICEPROID", nullable = true)
	private Long serviceProId;

	@OneToMany(targetEntity = OrderList.class)
	@JoinColumn(name = "VOUCHER_ID", updatable = true)
	private Set<OrderList> orderLists = new HashSet<OrderList>();

	@Column(name = "USECOUNT", nullable = true)
	private Long useCount;

	@Column(name = "NUMBER", nullable = false, unique = true)
	private String number;

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public User getOwnUser() {
		return ownUser;
	}

	public void setOwnUser(User ownUser) {
		this.ownUser = ownUser;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Long getServiceProId() {
		return serviceProId;
	}

	public void setServiceProId(Long serviceProId) {
		this.serviceProId = serviceProId;
	}

	public Set<OrderList> getOrderLists() {
		return orderLists;
	}

	public void setOrderLists(Set<OrderList> orderLists) {
		this.orderLists = orderLists;
	}

	public Long getUseCount() {
		return useCount;
	}

	public void setUseCount(Long useCount) {
		this.useCount = useCount;
	}

}
