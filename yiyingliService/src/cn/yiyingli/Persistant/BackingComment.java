package cn.yiyingli.Persistant;

import javax.persistence.*;

@Entity
@Table(name = "BACKINGCOMMENT")
public class BackingComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BACKINGCOMMENT_ID")
	private Long id;

	@Column(name = "CONTENT", nullable = false, length = 500)
	private String content;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User user;

	@Column(name = "WEIGHT")
	private Short weight;

	@Column(name = "DISPLAY")
	private Boolean display;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Short getWeight() {
		return weight;
	}

	public void setWeight(Short weight) {
		this.weight = weight;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}
}
