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
@Table(name = "WORKEXPERIENCE")
public class WorkExperience {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WORKEXPERIENCE_ID")
	private Long id;

	@Column(name = "COMPANYNAME", nullable = false)
	private String companyName;

	@Column(name = "POSITION", nullable = false)
	private String position;

	@Column(name = "STARTTIME", nullable = false)
	private String startTime;

	@Column(name = "ENDTIME", nullable = false)
	private String endTime;

	@Column(name = "DESCRIPTION", nullable = false, length = 500)
	private String description;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher ownTeacher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Teacher getOwnTeacher() {
		return ownTeacher;
	}

	public void setOwnTeacher(Teacher ownTeacher) {
		this.ownTeacher = ownTeacher;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
