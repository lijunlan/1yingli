package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LINKININFO")
public class LinkinInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LINKININFO_ID")
	private Long id;

//	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
//	@JoinColumn(name = "USER_ID", updatable = false)
//	private User ownUser;

	@Column(name = "LINKINID", unique = true)
	private String linkinId;

	@Column(name = "LINKINNAME", unique = true)
	private String linkinName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public User getOwnUser() {
//		return ownUser;
//	}
//
//	public void setOwnUser(User ownUser) {
//		this.ownUser = ownUser;
//	}

	public String getLinkinId() {
		return linkinId;
	}

	public void setLinkinId(String linkinId) {
		this.linkinId = linkinId;
	}
}
