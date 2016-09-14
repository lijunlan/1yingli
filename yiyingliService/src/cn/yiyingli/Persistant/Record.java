package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECORD")
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECORD_ID")
	private Long id;

	@Column(name = "CREATETIME", nullable = false)
	private String createTime;

	/**
	 * 访问者类型
	 */
	@Column(name = "TYPE", nullable = false)
	private short type;

	/**
	 * 事件类型
	 */
	@Column(name = "KIND", nullable = false)
	private short kind;

	/**
	 * 记录相关数据,json
	 */
	@Column(name = "DATA", nullable = true)
	private String data;

	/**
	 * 执行操作的IP
	 */
	@Column(name = "IP", nullable = false)
	private String ip;

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

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getKind() {
		return kind;
	}

	public void setKind(short kind) {
		this.kind = kind;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
