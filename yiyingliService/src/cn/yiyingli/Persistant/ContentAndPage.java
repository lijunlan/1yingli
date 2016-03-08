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
@Table(name = "CONTENTANDPAGE")
public class ContentAndPage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTENTANDPAGE_ID")
	private Long id;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Pages pages;

	@ManyToOne(targetEntity = ServicePro.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEPRO_ID", updatable = false)
	private ServicePro servicePro;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@Column(name = "ACTIVITYDES", nullable = true)
	private String activityDes;

	@ManyToOne(targetEntity = Passage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PASSAGE_ID", updatable = false)
	private Passage passage;

	@Column(name = "WEIGHT", nullable = false)
	private Integer weight;

	@Column(name = "CREATETIME", nullable = false, updatable = false)
	private String createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pages getPages() {
		return pages;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public ServicePro getServicePro() {
		return servicePro;
	}

	public void setServicePro(ServicePro servicePro) {
		this.servicePro = servicePro;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Passage getPassage() {
		return passage;
	}

	public void setPassage(Passage passage) {
		this.passage = passage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getActivityDes() {
		return activityDes;
	}

	public void setActivityDes(String activityDes) {
		this.activityDes = activityDes;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
