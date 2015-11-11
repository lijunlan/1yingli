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
@Table(name = "TSERVICE")
public class TService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TSERVICE_ID")
	private Long id;

	@Column(name = "PRICETOTAL", nullable = false)
	private Float priceTotal;

	@Column(name = "TIME", nullable = false)
	private Float time;

	@Column(name = "UPDATETIME", nullable = true)
	private String updateTime;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "CONTENT", nullable = false, length = 1000)
	private String content;

	@Column(name = "TIMESPERWEEK", nullable = true)
	private Integer timesPerWeek;

	@Column(name = "REASON", nullable = false, length = 500)
	private String reason;

	@Column(name = "ADVANTAGE", nullable = false, length = 1000)
	private String advantage;

	@OneToOne
	@JoinColumn(name = "TEACHER_ID", insertable = true, unique = true, updatable = true)
	private Teacher teacher;

	@OneToMany(targetEntity = Order.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TSERVICE_ID", updatable = false)
	private Set<Order> orders = new HashSet<Order>();
	
	@Column(name = "FREETIME", nullable = true)
	private String freeTime;

	@OneToMany(targetEntity = FreeTime.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TSERVICE_ID", updatable = false)
	private Set<FreeTime> freeTimes = new HashSet<FreeTime>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Float priceTotal) {
		this.priceTotal = priceTotal;
	}

	public Float getTime() {
		return time;
	}

	public void setTime(Float time) {
		this.time = time;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTimesPerWeek() {
		return timesPerWeek;
	}

	public void setTimesPerWeek(Integer timesPerWeek) {
		this.timesPerWeek = timesPerWeek;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<FreeTime> getFreeTimes() {
		return freeTimes;
	}

	public void setFreeTimes(Set<FreeTime> freeTimes) {
		this.freeTimes = freeTimes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

}
