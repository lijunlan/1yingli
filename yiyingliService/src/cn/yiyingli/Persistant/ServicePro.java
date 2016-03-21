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
@Table(name = "SERVICEPRO")
public class ServicePro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SERVICEPRO_ID")
	private Long id;

	@Column(name = "IMAGEURLS", nullable = false)
	private String imageUrls;

	@Column(name = "TIPS", nullable = false)
	private String tips;

	@Column(name = "TALKWAY", nullable = false)
	private Short talkWay;

	@Column(name = "ADDRESS", nullable = true)
	private String address;

	@Column(name = "CONTENT", nullable = false, length = 1000)
	private String content;

	@Column(name = "FREETIME", nullable = false)
	private String freeTime;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "SUMMARY", nullable = false)
	private String summary;

	@Column(name = "PRICE", nullable = false)
	private Float price;

	@Column(name = "PRICETEMP", nullable = false)
	private Float priceTemp;

	@Column(name = "ONSALE", nullable = false)
	private Boolean onSale;

	@Column(name = "UPDATETIME", nullable = false)
	private String updateTime;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "QUANTIFIER", nullable = false)
	private String quantifier;

	@Column(name = "NUMERAL", nullable = false)
	private Float numeral;

	@Column(name = "LIKENO", nullable = false)
	private Long likeNo;

	@Column(name = "NUMBER", nullable = false)
	private Integer number;

	@Column(name = "ACCEPTNO", nullable = false)
	private Long acceptNo;

	@Column(name = "FINISHNO", nullable = false)
	private Long finishNo;

	@Column(name = "MASKNO", nullable = false)
	private Long maskNo;

	@Column(name = "MASKFINISHNO", nullable = false)
	private Long maskFinishNo;

	@Column(name = "ORDERALLNO", nullable = false)
	private Long orderAllNo;

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEPRO_ID", updatable = true)
	private Set<Comment> comments = new HashSet<Comment>();

	@Column(name = "SCORE", nullable = false)
	private Float score;

	@Column(name = "ANSWERTIME", nullable = false)
	private Long answerTime;

	@Column(name = "ANSWERRATIO", nullable = false)
	private Float answerRatio;

	@Column(name = "PRAISERATIO", nullable = false)
	private Float praiseRatio;

	@Column(name = "KIND", nullable = false)
	private Integer kind;

	@Column(name = "STATE", nullable = false)
	private Short state;

	@Column(name = "ONSHOW", nullable = false)
	private Boolean onShow;

	@Column(name = "LOOKNUMBER", nullable = false)
	private Long lookNumber;

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = true)
	private Teacher teacher;

	@Column(name = "REMOVE", nullable = false)
	private Boolean remove;

	@Column(name = "COMMENTNO", nullable = false)
	private Long commentNo;

	@OneToMany(targetEntity = ContentAndPage.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEPRO_ID", updatable = false)
	private Set<ContentAndPage> contentAndPages = new HashSet<ContentAndPage>();

	@OneToMany(targetEntity = UserLikeServicePro.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEPRO_ID", updatable = false)
	private Set<UserLikeServicePro> userLikeServicePros = new HashSet<UserLikeServicePro>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getPriceTemp() {
		return priceTemp;
	}

	public void setPriceTemp(Float priceTemp) {
		this.priceTemp = priceTemp;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getQuantifier() {
		return quantifier;
	}

	public void setQuantifier(String quantifier) {
		this.quantifier = quantifier;
	}

	public Float getNumeral() {
		return numeral;
	}

	public void setNumeral(Float numeral) {
		this.numeral = numeral;
	}

	public Long getLikeNo() {
		return likeNo;
	}

	public void setLikeNo(Long likeNo) {
		this.likeNo = likeNo;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getFinishNo() {
		return finishNo;
	}

	public void setFinishNo(Long finishNo) {
		this.finishNo = finishNo;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
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

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Boolean getOnShow() {
		return onShow;
	}

	public void setOnShow(Boolean onShow) {
		this.onShow = onShow;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public Long getAcceptNo() {
		return acceptNo;
	}

	public void setAcceptNo(Long acceptNo) {
		this.acceptNo = acceptNo;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(Long commentNo) {
		this.commentNo = commentNo;
	}

	public Short getTalkWay() {
		return talkWay;
	}

	public void setTalkWay(Short talkWay) {
		this.talkWay = talkWay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<UserLikeServicePro> getUserLikeServicePros() {
		return userLikeServicePros;
	}

	public void setUserLikeServicePros(Set<UserLikeServicePro> userLikeServicePros) {
		this.userLikeServicePros = userLikeServicePros;
	}

	public Long getOrderAllNo() {
		return orderAllNo;
	}

	public void setOrderAllNo(Long orderAllNo) {
		this.orderAllNo = orderAllNo;
	}

	public Set<ContentAndPage> getContentAndPages() {
		return contentAndPages;
	}

	public void setContentAndPages(Set<ContentAndPage> contentAndPages) {
		this.contentAndPages = contentAndPages;
	}

	public Long getMaskNo() {
		return maskNo;
	}

	public void setMaskNo(Long maskNo) {
		this.maskNo = maskNo;
	}

	public Long getMaskFinishNo() {
		return maskFinishNo;
	}

	public void setMaskFinishNo(Long maskFinishNo) {
		this.maskFinishNo = maskFinishNo;
	}

	public Long getLookNumber() {
		return lookNumber;
	}

	public void setLookNumber(Long lookNumber) {
		this.lookNumber = lookNumber;
	}

}
