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
	private String key;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

}
