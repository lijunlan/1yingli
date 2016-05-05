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
@Table(name = "COMMENT")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENT_ID")
	private Long id;

	@Column(name = "SCORE", nullable = false)
	private Short score;

	@Column(name = "CONTENT", nullable = false, length = 500)
	private String content;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "KIND", nullable = false)
	private Short kind;

	@Column(name = "SERVICETITLE", nullable = false)
	private String serviceTitle;

	@Column(name = "PICS",columnDefinition="TEXT")
	private String pics;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User user;

	@ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private Order order;

	@ManyToOne(targetEntity = ServicePro.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEPRO_ID", updatable = true)
	private ServicePro servicePro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getScore() {
		return score;
	}

	public void setScore(Short score) {
		this.score = score;
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

	public Short getKind() {
		return kind;
	}

	public void setKind(Short kind) {
		this.kind = kind;
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

	public String getServiceTitle() {
		return serviceTitle;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public ServicePro getServicePro() {
		return servicePro;
	}

	public void setServicePro(ServicePro servicePro) {
		this.servicePro = servicePro;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}
}
