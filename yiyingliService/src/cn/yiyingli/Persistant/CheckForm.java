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
@Table(name = "CHECKFORM")
public class CheckForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHECKFORM_ID")
	private Long id;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@ManyToOne(targetEntity = Manager.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID", updatable = false)
	private Manager endManager;

	@Column(name = "ENDTIME", nullable = true)
	private String endTime;

	@Column(name = "STATE", nullable = false)
	private short state;

	@Column(name = "DETAIL", nullable = true)
	private String detail;

	@Column(name = "CHECKINFO", nullable = true)
	private String checkInfo;

	@Column(name = "URL", nullable = true)
	private String url;

	@Column(name = "KIND", nullable = false)
	private Short kind;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getKind() {
		return kind;
	}

	public void setKind(Short kind) {
		this.kind = kind;
	}

	public Manager getEndManager() {
		return endManager;
	}

	public void setEndManager(Manager endManager) {
		this.endManager = endManager;
	}

}
