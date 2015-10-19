package cn.yiyingli.Persistant;

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

	@Column(name = "USED", nullable = false)
	private Boolean used;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User ownUser;

	@ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private Order useOrder;

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

	public Order getUseOrder() {
		return useOrder;
	}

	public void setUseOrder(Order useOrder) {
		this.useOrder = useOrder;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
