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
@Table(name = "STUDYEXPERIENCE")
public class StudyExperience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDYEXPERIENCE_ID")
	private Long id;

	@Column(name = "SCHOOLNAME", nullable = false)
	private String schoolName;

	@Column(name = "DEGREE", nullable = false)
	private String degree;

	@Column(name = "MAJOR", nullable = false)
	private String major;

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

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
