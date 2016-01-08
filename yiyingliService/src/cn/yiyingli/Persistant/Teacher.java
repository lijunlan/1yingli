package cn.yiyingli.Persistant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "TEACHER")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEACHER_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID", insertable = false, unique = true, updatable = true)
	private User user;

	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@Column(name = "LIKENUMBER", nullable = false)
	private Long likeNumber;

	@Column(name = "PASSAGENUMBER", nullable = false)
	private Long passageNumber;

	@Column(name = "CHECKPASSAGENUMBER", nullable = false)
	private Long checkPassageNumber;

	@Column(name = "REFUSEPASSAGENUMBER", nullable = false)
	private Long refusePassageNumber;

	@Column(name = "INTRODUCE", nullable = true, length = 500)
	private String introduce;

	@Column(name = "ICONURL", nullable = true)
	private String iconUrl;

	@Column(name = "TIPMARK", nullable = false)
	private Long tipMark;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PHONE", unique = false)
	private String phone;

	@Column(name = "SEX", nullable = true)
	private Short sex;

	@Column(name = "SCORE", nullable = false)
	private Integer score;

	@Column(name = "ANSWERTIME", nullable = false)
	private Long answerTime;

	@Column(name = "ANSWERRATIO", nullable = false)
	private Float answerRatio;

	@Column(name = "PRAISERATIO", nullable = false)
	private Float praiseRatio;

	@Column(name = "SHOWWEIGHT1", nullable = true)
	private Integer showWeight1;

	@Column(name = "SHOWWEIGHT2", nullable = true)
	private Integer showWeight2;

	@Column(name = "SHOWWEIGHT4", nullable = true)
	private Integer showWeight4;

	@Column(name = "SHOWWEIGHT8", nullable = true)
	private Integer showWeight8;

	@Column(name = "SHOWWEIGHT16", nullable = true)
	private Integer showWeight16;

	@Column(name = "HOMEWEIGHT", nullable = true)
	private Integer homeWeight;

	@Column(name = "SALEWEIGHT", nullable = true)
	private Integer saleWeight;

	@Column(name = "LEVEL", nullable = false)
	private Short level;

	@Column(name = "BGURL", nullable = true)
	private String bgUrl;

	@Column(name = "ALIPAY", nullable = true)
	private String alipay;

	@Column(name = "PAYPAL", nullable = true)
	private String paypal;

	@Column(name = "CHECKEMAIL", nullable = false)
	private Boolean checkEmail;

	@Column(name = "CHECKPHONE", nullable = false)
	private Boolean checkPhone;

	@Column(name = "SERVICEPRONUMBERFORUSER", nullable = false)
	private Integer serviceProNumberForUser;

	@Column(name = "SERVICEPRONUMBERFORTEACHER", nullable = false)
	private Integer serviceProNumberForTeacher;

	@Column(name = "COMMENTNUMBER", nullable = false)
	private Long commentNumber;

	@Column(name = "ORDERNUMBER", nullable = false)
	private Long orderNumber;

	@Column(name = "FINISHORDERNUMBER", nullable = false)
	private Long finishOrderNumber;

	@Column(name = "MILE", nullable = false)
	private Long mile;

	@Column(name = "CHECKIDCARDSTATE", nullable = false)
	private Short checkIDCardState;

	@Column(name = "CHECKWORKSTATE", nullable = false)
	private Short checkWorkState;

	@Column(name = "CHECKDEGREESTATE", nullable = false)
	private Short checkDegreeState;

	@Column(name = "SIMPLEINFO", nullable = true)
	private String simpleInfo;

	@Column(name = "ONSERVICE", nullable = false)
	private Boolean onService;

	@OneToMany(targetEntity = ServicePro.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = true, insertable = true)
	private Set<ServicePro> servicePros = new HashSet<ServicePro>();

	@Column(name = "FIRSTIDENTITY", nullable = true)
	private String firstIdentity;

	@OneToMany(targetEntity = WorkExperience.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TEACHER_ID", updatable = true, insertable = true)
	@IndexColumn(name = "WORK_NO")
	private List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();

	@OneToMany(targetEntity = StudyExperience.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TEACHER_ID", updatable = true, insertable = true)
	@IndexColumn(name = "STUDY_NO", base = 0)
	private List<StudyExperience> studyExperiences = new ArrayList<StudyExperience>();

	@OneToMany(targetEntity = UserLikeTeacher.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<UserLikeTeacher> userLikeTeachers = new HashSet<UserLikeTeacher>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TEACHER_TIP", joinColumns = {
			@JoinColumn(name = "TEACHER_ID", referencedColumnName = "TEACHER_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "TIP_ID", referencedColumnName = "TIP_ID", nullable = false) })
	private Set<Tip> tips = new HashSet<Tip>();

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<Comment> comments = new HashSet<Comment>();

	@OneToMany(targetEntity = CheckForm.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<CheckForm> checkForms = new HashSet<CheckForm>();

	@OneToMany(targetEntity = Order.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<Order> receiveOrders = new HashSet<Order>();

	@OneToMany(targetEntity = OrderList.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<OrderList> receiveOrderLists = new HashSet<OrderList>();

	@OneToMany(targetEntity = Passage.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<Passage> passages = new HashSet<Passage>();

	@Column(name = "CREATETIME", nullable = true)
	private String createTime;

	@Column(name = "LOOKNUMBER", nullable = false)
	private Long lookNumber;

	@Column(name = "PRICE", nullable = true)
	private Float price;

	/**
	 * 导师默认的闲聊话题，需要跟服务中的同步
	 */
	@Column(name = "TOPIC", nullable = false)
	private String topic;

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Long getTipMark() {
		return tipMark;
	}

	public void setTipMark(Long tipMark) {
		this.tipMark = tipMark;
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

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public Boolean getCheckEmail() {
		return checkEmail;
	}

	public void setCheckEmail(Boolean checkEmail) {
		this.checkEmail = checkEmail;
	}

	public Boolean getCheckPhone() {
		return checkPhone;
	}

	public void setCheckPhone(Boolean checkPhone) {
		this.checkPhone = checkPhone;
	}

	public Short getCheckIDCardState() {
		return checkIDCardState;
	}

	public void setCheckIDCardState(Short checkIDCardState) {
		this.checkIDCardState = checkIDCardState;
	}

	public Short getCheckWorkState() {
		return checkWorkState;
	}

	public void setCheckWorkState(Short checkWorkState) {
		this.checkWorkState = checkWorkState;
	}

	public Short getCheckDegreeState() {
		return checkDegreeState;
	}

	public void setCheckDegreeState(Short checkDegreeState) {
		this.checkDegreeState = checkDegreeState;
	}

	public List<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(List<WorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

	public List<StudyExperience> getStudyExperiences() {
		return studyExperiences;
	}

	public void setStudyExperiences(List<StudyExperience> studyExperiences) {
		this.studyExperiences = studyExperiences;
	}

	public Set<Tip> getTips() {
		return tips;
	}

	public Set<UserLikeTeacher> getUserLikeTeachers() {
		return userLikeTeachers;
	}

	public void setUserLikeTeachers(Set<UserLikeTeacher> userLikeTeachers) {
		this.userLikeTeachers = userLikeTeachers;
	}

	public void setTips(Set<Tip> tips) {
		this.tips = tips;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Order> getReceiveOrders() {
		return receiveOrders;
	}

	public void setReceiveOrders(Set<Order> receiveOrders) {
		this.receiveOrders = receiveOrders;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFirstIdentity() {
		return firstIdentity;
	}

	public void setFirstIdentity(String firstIdentity) {
		this.firstIdentity = firstIdentity;
	}

	public Set<CheckForm> getCheckForms() {
		return checkForms;
	}

	public void setCheckForms(Set<CheckForm> checkForms) {
		this.checkForms = checkForms;
	}

	public Long getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(Long likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Long getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Long commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Boolean getOnService() {
		return onService;
	}

	public void setOnService(Boolean onService) {
		this.onService = onService;
	}

	public String getSimpleInfo() {
		return simpleInfo;
	}

	public void setSimpleInfo(String simpleInfo) {
		this.simpleInfo = simpleInfo;
	}

	public Long getLookNumber() {
		return lookNumber;
	}

	public void setLookNumber(Long lookNumber) {
		this.lookNumber = lookNumber;
	}

	public Integer getShowWeight1() {
		return showWeight1;
	}

	public Integer getShowWeight2() {
		return showWeight2;
	}

	public Integer getShowWeight4() {
		return showWeight4;
	}

	public Integer getShowWeight8() {
		return showWeight8;
	}

	public Integer getShowWeight16() {
		return showWeight16;
	}

	public void setShowWeight1(Integer showWeight1) {
		this.showWeight1 = showWeight1;
	}

	public void setShowWeight2(Integer showWeight2) {
		this.showWeight2 = showWeight2;
	}

	public void setShowWeight4(Integer showWeight4) {
		this.showWeight4 = showWeight4;
	}

	public void setShowWeight8(Integer showWeight8) {
		this.showWeight8 = showWeight8;
	}

	public void setShowWeight16(Integer showWeight16) {
		this.showWeight16 = showWeight16;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getHomeWeight() {
		return homeWeight;
	}

	public void setHomeWeight(Integer homeWeight) {
		this.homeWeight = homeWeight;
	}

	public Integer getSaleWeight() {
		return saleWeight;
	}

	public void setSaleWeight(Integer saleWeight) {
		this.saleWeight = saleWeight;
	}

	public Long getPassageNumber() {
		return passageNumber;
	}

	public void setPassageNumber(Long passageNumber) {
		this.passageNumber = passageNumber;
	}

	public Long getCheckPassageNumber() {
		return checkPassageNumber;
	}

	public void setCheckPassageNumber(Long checkPassageNumber) {
		this.checkPassageNumber = checkPassageNumber;
	}

	public Long getRefusePassageNumber() {
		return refusePassageNumber;
	}

	public void setRefusePassageNumber(Long refusePassageNumber) {
		this.refusePassageNumber = refusePassageNumber;
	}

	public Set<Passage> getPassages() {
		return passages;
	}

	public void setPassages(Set<Passage> passages) {
		this.passages = passages;
	}

	public Long getFinishOrderNumber() {
		return finishOrderNumber;
	}

	public void setFinishOrderNumber(Long finishOrderNumber) {
		this.finishOrderNumber = finishOrderNumber;
	}

	public Long getMile() {
		return mile;
	}

	public void setMile(Long mile) {
		this.mile = mile;
	}

	public String getPaypal() {
		return paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}

	public Set<ServicePro> getServicePros() {
		return servicePros;
	}

	public void setServicePros(Set<ServicePro> servicePros) {
		this.servicePros = servicePros;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Set<OrderList> getReceiveOrderLists() {
		return receiveOrderLists;
	}

	public void setReceiveOrderLists(Set<OrderList> receiveOrderLists) {
		this.receiveOrderLists = receiveOrderLists;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}

	public Float getAnswerRatio() {
		return answerRatio;
	}

	public void setAnswerRatio(Float answerRatio) {
		this.answerRatio = answerRatio;
	}

	public Float getPraiseRatio() {
		return praiseRatio;
	}

	public void setPraiseRatio(Float praiseRatio) {
		this.praiseRatio = praiseRatio;
	}

	public Integer getServiceProNumberForUser() {
		return serviceProNumberForUser;
	}

	public void setServiceProNumberForUser(Integer serviceProNumberForUser) {
		this.serviceProNumberForUser = serviceProNumberForUser;
	}

	public Integer getServiceProNumberForTeacher() {
		return serviceProNumberForTeacher;
	}

	public void setServiceProNumberForTeacher(Integer serviceProNumberForTeacher) {
		this.serviceProNumberForTeacher = serviceProNumberForTeacher;
	}

}
