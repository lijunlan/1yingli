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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "NAME", nullable = true)
	private String name;

	@Column(name = "NICKNAME", nullable = true)
	private String nickName;

	@Column(name = "RESUME", nullable = true, length = 500)
	private String resume;

	@Column(name = "ADDRESS", nullable = true)
	private String address;

	@Column(name = "ICONURL", nullable = true)
	private String iconUrl;

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;

	@Column(name = "PHONE", nullable = true)
	private String phone;

	@Column(name = "LASTLOGINTIME", nullable = true)
	private String lastLoginTime;

	@Column(name = "SEX", nullable = true)
	private Short sex;

	@Column(name = "WECHATNO", nullable = true, unique = true)
	private String wechatNo;

	@Column(name = "WEIBONO", nullable = true, unique = true)
	private String weiboNo;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "TEACHERSTATE", nullable = false)
	private Short teacherState;

	@Column(name = "LIKETEACHERNUMBER", nullable = false)
	private Long likeTeacherNumber;

	@Column(name = "RECEIVECOMMENTNUMBER", nullable = false)
	private Long receiveCommentNumber;

	@Column(name = "SENDCOMMENTNUMBER", nullable = false)
	private Long sendCommentNumber;

	@Column(name = "ORDERNUMBER", nullable = false)
	private Long orderNumber;

	@Column(name = "FORBID", nullable = false)
	private Boolean forbid;

	@OneToMany(targetEntity = ApplicationForm.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Set<ApplicationForm> applicationForms = new HashSet<ApplicationForm>();

	// @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	// @JoinTable(name = "USER_LIKE_TEACHER", joinColumns = {
	// @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable
	// = false) }, inverseJoinColumns = {
	// @JoinColumn(name = "TEACHER_ID", referencedColumnName = "TEACHER_ID",
	// nullable = false) })
	// private Set<Teacher> likeTeahcers = new HashSet<Teacher>();

	@OneToMany(targetEntity = UserLikeTeacher.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Set<UserLikeTeacher> userLikeTeachers = new HashSet<UserLikeTeacher>();

	@OneToMany(targetEntity = Notification.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Set<Notification> notifications = new HashSet<Notification>();

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Set<Comment> comments = new HashSet<Comment>();

	@OneToMany(targetEntity = Voucher.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Set<Voucher> ownSiteDiscounts = new HashSet<Voucher>();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Teacher teacher;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private UserMark userMark;

	@OneToMany(targetEntity = Order.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private Set<Order> orders = new HashSet<Order>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Short getTeacherState() {
		return teacherState;
	}

	public void setTeacherState(Short teacherState) {
		this.teacherState = teacherState;
	}

	public Boolean getForbid() {
		return forbid;
	}

	public void setForbid(Boolean forbid) {
		this.forbid = forbid;
	}

	// public Set<Teacher> getLikeTeahcers() {
	// return likeTeahcers;
	// }
	//
	// public void setLikeTeahcers(Set<Teacher> likeTeahcers) {
	// this.likeTeahcers = likeTeahcers;
	// }

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public Set<UserLikeTeacher> getUserLikeTeachers() {
		return userLikeTeachers;
	}

	public void setUserLikeTeachers(Set<UserLikeTeacher> userLikeTeachers) {
		this.userLikeTeachers = userLikeTeachers;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Voucher> getOwnSiteDiscounts() {
		return ownSiteDiscounts;
	}

	public void setOwnSiteDiscounts(Set<Voucher> ownSiteDiscounts) {
		this.ownSiteDiscounts = ownSiteDiscounts;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public UserMark getUserMark() {
		return userMark;
	}

	public void setUserMark(UserMark userMark) {
		this.userMark = userMark;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<ApplicationForm> getApplicationForms() {
		return applicationForms;
	}

	public void setApplicationForms(Set<ApplicationForm> applicationForms) {
		this.applicationForms = applicationForms;
	}

	public Long getLikeTeacherNumber() {
		return likeTeacherNumber;
	}

	public void setLikeTeacherNumber(Long likeTeacherNumber) {
		this.likeTeacherNumber = likeTeacherNumber;
	}

	public Long getReceiveCommentNumber() {
		return receiveCommentNumber;
	}

	public void setReceiveCommentNumber(Long receiveCommentNumber) {
		this.receiveCommentNumber = receiveCommentNumber;
	}

	public Long getSendCommentNumber() {
		return sendCommentNumber;
	}

	public void setSendCommentNumber(Long sendCommentNumber) {
		this.sendCommentNumber = sendCommentNumber;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getWeiboNo() {
		return weiboNo;
	}

	public void setWeiboNo(String weiboNo) {
		this.weiboNo = weiboNo;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

}
