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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PASSAGE")
public class Passage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PASSAGE_ID")
	private Long id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "LIKENUMBER", nullable = false)
	private Long likeNumber;

	@Column(name = "LOOKNUMBER", nullable = false)
	private Long lookNumber;

	@Column(name = "EDITORNAME", nullable = false)
	private String editorName;

	@Column(name = "SUMMARY", nullable = false)
	private String summary;

	@Column(name = "CONTENT", nullable = false, length = 1000)
	private String content;

	@Column(name = "TAG", nullable = false)
	private String tag;

	@Column(name = "IMAGEURL", nullable = false)
	private String imageUrl;

	@Column(name = "ONSHOW", nullable = false)
	private Boolean onshow;

	@Column(name = "STATE", nullable = false)
	private Short state;

	@Column(name = "REFUSEREASON", nullable = true)
	private String refuseReason;

	@Column(name = "REMOVE", nullable = false)
	private Boolean remove;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher ownTeacher;

	@OneToMany(targetEntity = UserLikePassage.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "PASSAGE_ID", updatable = false)
	private Set<UserLikePassage> userLikePassages = new HashSet<UserLikePassage>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(Long likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Long getLookNumber() {
		return lookNumber;
	}

	public void setLookNumber(Long lookNumber) {
		this.lookNumber = lookNumber;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getOnshow() {
		return onshow;
	}

	public void setOnshow(Boolean onshow) {
		this.onshow = onshow;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Teacher getOwnTeacher() {
		return ownTeacher;
	}

	public void setOwnTeacher(Teacher ownTeacher) {
		this.ownTeacher = ownTeacher;
	}

	public Set<UserLikePassage> getUserLikePassages() {
		return userLikePassages;
	}

	public void setUserLikePassages(Set<UserLikePassage> userLikePassages) {
		this.userLikePassages = userLikePassages;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

}
