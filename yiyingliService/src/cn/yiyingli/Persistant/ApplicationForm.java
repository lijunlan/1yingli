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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "APPLICATIONFORM")
public class ApplicationForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPLICATIONFORM_ID")
	private Long id;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User user;

	@ManyToOne(targetEntity = Manager.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID", updatable = false)
	private Manager endManager;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Teacher teacher;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "ENDTIME", nullable = true)
	private String endTime;

	@Column(name = "STATE", nullable = false)
	private short state;

	@Column(name = "CHECKINFO", nullable = true)
	private String checkInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public Manager getEndManager() {
		return endManager;
	}

	public void setEndManager(Manager endManager) {
		this.endManager = endManager;
	}

}
