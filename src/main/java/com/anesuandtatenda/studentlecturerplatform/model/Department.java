package com.anesuandtatenda.studentlecturerplatform.model;

import com.anesuandtatenda.studentlecturerplatform.local.requests.DepartmentCreateRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.DepartmentUpdateRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.DepartmentRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author GustTech
 */
@Entity
@Table(name = "department")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id")
	private long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name")
	private String name;

	@Size(max = 250)
	@Column(name = "description")
	private String description;

	@JoinColumn(name = "facaulty_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Facaulty facaulty;

	public Department() {
	}

	public Department(Integer id) {
		this.id = id;
	}

	public Department(Integer id, String name) {
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

	public Facaulty getFacaulty() {
		return facaulty;
	}

	public void setFacaulty(Facaulty facaulty) {
		this.facaulty = facaulty;
	}

	public static Department fromCommand(DepartmentRequest request) {

		if (request == null) {
			return null;
		}

		Department department = new Department();
		department.setName(request.getName());
		department.setDescription(request.getDescription());

		return department;
	}

	public void update(DepartmentUpdateRequest updateRequest) {
		this.setName(updateRequest.getName());
		this.setDescription(updateRequest.getDescription());
		this.setFacaulty(updateRequest.getFacaulty());
	}

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", facaulty=" + facaulty +
				'}';
	}
}
