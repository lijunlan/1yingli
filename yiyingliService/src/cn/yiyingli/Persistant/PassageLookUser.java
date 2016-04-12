package cn.yiyingli.Persistant;


import javax.persistence.*;

@Entity
@Table(name = "PASSAGELOOKUSER")
public class PassageLookUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PASSAGELOOKUSER_ID")
	private long id;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(targetEntity = Passage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PASSAGE_ID")
	private Passage passage;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Passage getPassage() {
		return passage;
	}

	public void setPassage(Passage passage) {
		this.passage = passage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
