package com.anesuandtatenda.studentlecturerplatform.model;

import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
import com.anesuandtatenda.studentlecturerplatform.web.requests.AccountRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author GustTech
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "last_name")
	private String lastName;

	@JsonIgnore
	@Size(min = 1, max = 50)
	@Column(name = "username")
	private String username;

	@Column(name = "year")
	private int year;

	@Size(max = 50)
	@Column(name = "reg_number",unique = true)
	private String regNumber;

	@ManyToOne
	private Programs program;

	@Enumerated(EnumType.ORDINAL)
	private Role role;

	@Column(name="password")
	private String password;

	public Account() {
	}

	public Account(Integer id) {
		this.id = id;
	}

	public Account(Integer id, String firstName, String lastName,String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password=password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Programs getProgram() {
		return program;
	}

	public void setProgram(Programs program) {
		this.program = program;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Account fromCommand(AccountRequest request) {

		if (request == null) {
			return null;
		}

		Account account = new Account();
		account.setFirstName(request.getFirstName());
		account.setLastName(request.getLastName());
		account.setUsername("xxx");
		account.setYear(request.getYear());
		account.setPassword(request.getPassword());
		account.setRole(request.getRole());

		return account;
	}

	public void update(Account updateRequest) {
		this.setFirstName(updateRequest.getFirstName());
		this.setLastName(updateRequest.getLastName());
		this.setUsername(updateRequest.getUsername());
		this.setRegNumber(updateRequest.getRegNumber());
		this.setYear(updateRequest.getYear());
		this.setProgram(updateRequest.getProgram());
		this.setRole(updateRequest.getRole());
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", year=" + year +
				", regNumber='" + regNumber + '\'' +
				", program=" + program +
				", role=" + role +
				'}';
	}
}
