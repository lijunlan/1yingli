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

	@Column(name = "CREATETIME", nullable = false)
	private String description;

	@Column(name = "CREATETIME", nullable = true)
	private String startTime;

	@Column(name = "CREATETIME", nullable = true)
	private String endTime;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@OneToMany(targetEntity = ContentAndPage.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "PAGES_ID", updatable = false)
	private Set<ContentAndPage> contentAndPages = new HashSet<ContentAndPage>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
