package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REWARD")
public class Reward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REWARD_ID")
	private Long id;

	@Column(name = "MONEY", nullable = false)
	private Float money;

	@Column(name = "TEACHER_ID", nullable = false)
	private Long teacherId;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "REWARDNO", nullable = true, unique = true)
	private String rewardNo;

	@Column(name = "FINISHPAY", nullable = false)
	private Boolean finishPay;

	@Column(name = "FINISHSALARY", nullable = false)
	private Boolean finishSalary;

	@Column(name = "PAYTIME", nullable = true)
	private String payTime;

	@Column(name = "TEACHERNAME", nullable = false)
	private String teacherName;

	@Column(name = "USERNAME", nullable = true)
	private String userName;

	@Column(name = "USER_ID", nullable = true)
	private Long userId;

	@Column(name = "PASSAGE_ID", nullable = true)
	private Long passageId;

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

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Boolean getFinishPay() {
		return finishPay;
	}

	public void setFinishPay(Boolean finishPay) {
		this.finishPay = finishPay;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getFinishSalary() {
		return finishSalary;
	}

	public void setFinishSalary(Boolean finishSalary) {
		this.finishSalary = finishSalary;
	}

	public String getRewardNo() {
		return rewardNo;
	}

	public void setRewardNo(String rewardNo) {
		this.rewardNo = rewardNo;
	}

	public Long getPassageId() {
		return passageId;
	}

	public void setPassageId(Long passageId) {
		this.passageId = passageId;
	}

}
