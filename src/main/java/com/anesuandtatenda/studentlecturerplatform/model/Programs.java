package com.anesuandtatenda.studentlecturerplatform.model;

import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramCreateRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramUpdateRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author GustTech
 */
@Entity
@Table(name = "programs")
public class Programs implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name")
	private String name;

	@Size(max = 250)
	@Column(name = "description")
	private String description;

	@JoinColumn(name = "department_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Department department;

	public Programs() {
	}

	public Programs(Integer id) {
		this.id = id;
	}

	public Programs(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	public static Programs fromCommand(ProgramCreateRequest request) {

		if (request == null) {
			return null;
		}

		Programs program = new Programs();
		program.setName(request.getName());
		program.setDescription(request.getDescription());

		return program;
	}

	public void update(ProgramUpdateRequest updateRequest) {
		this.setName(updateRequest.getName());
		this.setDescription(updateRequest.getDescription());
		this.setDepartment(updateRequest.getDepartment());
	}

	@Override
	public String toString() {
		return "Programs{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", department=" + department +
				'}';
	}
}
