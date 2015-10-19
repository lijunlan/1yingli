package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BACKGROUND")
public class Background {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BACKGROUND_ID")
	private Long id;

	@Column(name = "URL", nullable = false)
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
