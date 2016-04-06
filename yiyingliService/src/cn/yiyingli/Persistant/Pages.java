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
import javax.persistence.Table;

@Entity
@Table(name = "PAGES")
public class Pages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAGES_ID")
	private Long id;

	@Column(name = "PAGESKEY", nullable = false, unique = true)
	private String pagesKey;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "STARTTIME", nullable = true)
	private String startTime;

	@Column(name = "ENDTIME", nullable = true)
	private String endTime;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "PASSAGECOUNT", nullable = false)
	private Integer passageCount;

	@Column(name = "SERVICEPROCOUNT", nullable = false)
	private Integer serviceProCount;

	@Column(name = "TEACHERCOUNT", nullable = false)
	private Integer teacherCount;

	@Column(name = "REMOVE", nullable = false)
	private Boolean remove;

	@OneToMany(targetEntity = ContentAndPage.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "PAGES_ID", updatable = false)
	private Set<ContentAndPage> contentAndPages = new HashSet<ContentAndPage>();

	@Column(name = "WEIGHT",nullable = true)
	private Long weight;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "CONTACT")
	private String contact;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "MILE")
	private Float mile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPagesKey() {
		return pagesKey;
	}

	public void setPagesKey(String pagesKey) {
		this.pagesKey = pagesKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<ContentAndPage> getContentAndPages() {
		return contentAndPages;
	}

	public void setContentAndPages(Set<ContentAndPage> contentAndPages) {
		this.contentAndPages = contentAndPages;
	}

	public Integer getPassageCount() {
		return passageCount;
	}

	public void setPassageCount(Integer passageCount) {
		this.passageCount = passageCount;
	}

	public Integer getServiceProCount() {
		return serviceProCount;
	}

	public void setServiceProCount(Integer serviceProCount) {
		this.serviceProCount = serviceProCount;
	}

	public Integer getTeacherCount() {
		return teacherCount;
	}

	public void setTeacherCount(Integer teacherCount) {
		this.teacherCount = teacherCount;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getMile() {
		return mile;
	}

	public void setMile(Float mile) {
		this.mile = mile;
	}
}
