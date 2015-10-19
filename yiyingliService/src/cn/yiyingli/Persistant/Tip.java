package cn.yiyingli.Persistant;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TIP")
public class Tip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TIP_ID")
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "tips", fetch = FetchType.LAZY)
	private Set<Teacher> teachers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

}
