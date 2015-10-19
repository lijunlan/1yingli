package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MANAGERMARK")
public class ManagerMark {
	@Id
	@Column(name = "UUID")
	private String uuid;

	@OneToOne
	@JoinColumn(name = "MANAGER_ID", insertable = true, unique = true)
	private Manager manager;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
