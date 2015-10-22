package cn.yiyingli.Persistant;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FREETIME")
public class FreeTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FREETIME_ID")
	private Long id;

	@Column(name = "LOCAL", nullable = false)
	private String local;

	@Column(name = "STARTTIME", nullable = false)
	private String startTime;

	@Column(name = "ENDTIME", nullable = false)
	private String endTime;

	@ManyToOne(targetEntity = TService.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "TSERVICE_ID", updatable = false)
	private Set<TService> tServices = new HashSet<TService>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Set<TService> gettServices() {
		return tServices;
	}

	public void settServices(Set<TService> tServices) {
		this.tServices = tServices;
	}
	
}
