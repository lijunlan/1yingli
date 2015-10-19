package cn.yiyingli.Persistant;

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
@Table(name = "MANAGER")
public class Manager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MANAGER_ID")
	private Long id;

	@Column(name = "USERNAME", unique = true)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "ROOT", nullable = false)
	private Short root;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "NO", nullable = false)
	private String no;

	@Column(name = "LASTLOGINTIME", nullable = true)
	private String lastLoginTime;

	@OneToOne(mappedBy = "manager", fetch = FetchType.LAZY)
	private ManagerMark managerMark;

	@OneToMany(targetEntity = CheckForm.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID", updatable = false)
	private Set<CheckForm> endCheckForms;

	@OneToMany(targetEntity = ApplicationForm.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID", updatable = false)
	private Set<ApplicationForm> endApplicationForms;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getRoot() {
		return root;
	}

	public void setRoot(Short root) {
		this.root = root;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public ManagerMark getManagerMark() {
		return managerMark;
	}

	public void setManagerMark(ManagerMark managerMark) {
		this.managerMark = managerMark;
	}

}
