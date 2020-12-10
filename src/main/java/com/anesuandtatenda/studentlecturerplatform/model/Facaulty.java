package com.anesuandtatenda.studentlecturerplatform.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author GustTech
 */
@Entity
@Table(name = "facaulty")
public class Facaulty implements Serializable {

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

	public Facaulty() {
	}

	public Facaulty(Integer id) {
		this.id = id;
	}

	public Facaulty(Integer id, String name) {
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


	public static Facaulty fromCommand(Facaulty request) {

		if (request == null) {
			return null;
		}

		Facaulty facaulty = new Facaulty();
		facaulty.setName(request.getName());
		facaulty.setDescription(request.getDescription());

		return facaulty;
	}

	public void update(Facaulty updateRequest) {
		this.setName(updateRequest.getName());
		this.setDescription(updateRequest.getDescription());
	}

	@Override
	public String toString() {
		return "Facaulty{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
