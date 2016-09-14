package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OPERATERECORD")
public class OperateRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPERATE_ID")
	private Long id;
	
	@Column(name = "TIMEMILLS", nullable = false)
	private long timeMills;

	//记录管理员名字
	@Column(name = "NAME", nullable = true)
	private String name;

	//记录调用的方法
	@Column(name = "OPERATE", nullable = false)
	private String operate;

	//记录调用的结果
	@Column(name = "RESULT", nullable = false)
	private String result;
	
	@Column(name = "IP", nullable = false)
	private String ip;
	
	@Column(name = "RECEIVE", nullable = false)
	private String receive;

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTimeMills() {
		return timeMills;
	}

	public void setTimeMills(long timeMills) {
		this.timeMills = timeMills;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
