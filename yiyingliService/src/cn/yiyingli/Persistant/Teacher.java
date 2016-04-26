package cn.yiyingli.Persistant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.bouncycastle.jcajce.provider.symmetric.TEA;
import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "TEACHER")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEACHER_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID", insertable = true, unique = true, updatable = false)
	private User user;

	@Column(name = "MASKNUMBER", nullable = false)
	private Long maskNumber;

	@Column(name = "MASKFINISHNUMBER", nullable = false)
	private Long maskFinishNumber;

	@Column(name = "SHOWNOTIFY", nullable = false)
	private Boolean showNotify;

	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@Column(name = "LIKENUMBER", nullable = false)
	private Long likeNumber;

	@Column(name = "REWARDNUMBER", nullable = false)
	private Long rewardNumber;

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

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "PHONE", nullable = true)
	private String phone;

	@Column(name = "SEX", nullable = true)
	private Short sex;

	@Column(name = "SCORE", nullable = false)
	private Float score;

	@Column(name = "ONCHAT", nullable = false)
	private Boolean onChat;

	@Column(name = "ANSWERTIME", nullable = false)
	private Long answerTime;

	@Column(name = "ANSWERRATIO", nullable = false)
	private Float answerRatio;

	@Column(name = "PRAISERATIO", nullable = false)
	private Float praiseRatio;

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

	@Column(name = "ORDERALLNUMBER", nullable = false)
	private Long orderAllNumber;

	@Column(name = "FINISHORDERNUMBER", nullable = false)
	private Long finishOrderNumber;

	@Column(name = "ACCEPTORDERNUMBER", nullable = false)
	private Long acceptOrderNumber;

	@Column(name = "MILE", nullable = false)
	private Float mile;

	@Column(name = "SUBMILE", nullable = false)
	private Long subMile;

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

	@OneToMany(targetEntity = ContentAndPage.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<ContentAndPage> contentAndPages = new HashSet<ContentAndPage>();

	@OneToMany(targetEntity = ServicePro.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = true, insertable = true)
	private Set<ServicePro> servicePros = new HashSet<ServicePro>();

	@Column(name = "FIRSTIDENTITY", nullable = true)
	private String firstIdentity;

	@OneToMany(targetEntity = WorkExperience.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TEACHER_ID", updatable = true, insertable = true)
	@IndexColumn(name = "WORK_NO", base = 0)
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
			@JoinColumn(name = "TEACHER_ID", referencedColumnName = "TEACHER_ID", nullable = false)}, inverseJoinColumns = {
			@JoinColumn(name = "TIP_ID", referencedColumnName = "TIP_ID", nullable = false)})
	private Set<Tip> tips = new HashSet<Tip>();

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<Comment> comments = new HashSet<Comment>();


	@OneToMany(targetEntity = BackingComment.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Set<BackingComment> backingComments = new HashSet<>();

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

	@Column(name = "INVITATIONCODE")
	private String invitationCode;


	@JoinColumn(name = "INVITERID", updatable = false)
	private Long inviterId;


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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Float getMile() {
		return mile;
	}

	public void setMile(Float mile) {
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

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
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

	public Long getRewardNumber() {
		return rewardNumber;
	}

	public void setRewardNumber(Long rewardNumber) {
		this.rewardNumber = rewardNumber;
	}

	public Long getOrderAllNumber() {
		return orderAllNumber;
	}

	public void setOrderAllNumber(Long orderAllNumber) {
		this.orderAllNumber = orderAllNumber;
	}

	public Long getAcceptOrderNumber() {
		return acceptOrderNumber;
	}

	public void setAcceptOrderNumber(Long acceptOrderNumber) {
		this.acceptOrderNumber = acceptOrderNumber;
	}

	public Long getMaskNumber() {
		return maskNumber;
	}

	public void setMaskNumber(Long maskNumber) {
		this.maskNumber = maskNumber;
	}

	public Long getMaskFinishNumber() {
		return maskFinishNumber;
	}

	public void setMaskFinishNumber(Long maskFinishNumber) {
		this.maskFinishNumber = maskFinishNumber;
	}

	public Boolean getOnChat() {
		return onChat;
	}

	public void setOnChat(Boolean onChat) {
		this.onChat = onChat;
	}

	public Set<ContentAndPage> getContentAndPages() {
		return contentAndPages;
	}

	public void setContentAndPages(Set<ContentAndPage> contentAndPages) {
		this.contentAndPages = contentAndPages;
	}

	public Long getSubMile() {
		return subMile;
	}

	public void setSubMile(Long subMile) {
		this.subMile = subMile;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public Boolean getShowNotify() {
		return showNotify;
	}

	public void setShowNotify(Boolean showNotify) {
		this.showNotify = showNotify;
	}


	public Set<BackingComment> getBackingComments() {
		return backingComments;
	}

	public void setBackingComments(Set<BackingComment> backingComments) {
		this.backingComments = backingComments;
	}
}
