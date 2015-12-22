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

	@Column(name = "TIPS", nullable = false)
	private String tips;

	@Column(name = "CONTENT", nullable = false, length = 1000)
	private String content;

	@Column(name = "FREETIME", nullable = false)
	private String freeTime;

	@Column(name = "TITLE", nullable = false)
	private String title;

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
	private Integer numeral;

	@Column(name = "LIKENO", nullable = false)
	private Long likeNo;

	@Column(name = "NUMBER", nullable = false)
	private Integer number;

	@Column(name = "FINISHNO", nullable = false)
	private Long finishNo;

	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEPRO_ID", updatable = false)
	private Set<Comment> comments = new HashSet<Comment>();

	@Column(name = "SCORE", nullable = false)
	private Integer score;

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

	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private Teacher teacher;

	@Column(name = "RANKNO", nullable = false)
	private Integer rankNo;

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

	public Integer getNumeral() {
		return numeral;
	}

	public void setNumeral(Integer numeral) {
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

	public Integer getRankNo() {
		return rankNo;
	}

	public void setRankNo(Integer rankNo) {
		this.rankNo = rankNo;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

}
