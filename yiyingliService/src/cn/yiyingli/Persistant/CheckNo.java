package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHECKNO")
public class CheckNo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHECKNO_ID")
	private Long id;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	@Column(name = "ENDTIME", nullable = false)
	private String endTime;

	@Column(name = "CHECKNO", nullable = false)
	private String checkNo;

	@Column(name = "USERNAME", unique = true)
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
