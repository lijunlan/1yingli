package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MIDKEEPER")
public class MidKeeper {
	@Id
	@GeneratedValue(generator="i-ass")
	@GenericGenerator(name="i-ass",strategy="assigned")
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME", nullable = true)
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
