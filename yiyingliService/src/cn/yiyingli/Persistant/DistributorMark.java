package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DISTRIBUTORMARK")
public class DistributorMark {

	@Id
	@Column(name = "UUID")
	private String uuid;

	@OneToOne
	@JoinColumn(name = "DISTRIBUTOR_ID", insertable = true, unique = true)
	private Distributor distributor;

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
