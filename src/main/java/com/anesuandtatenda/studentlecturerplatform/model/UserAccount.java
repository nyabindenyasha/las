package com.anesuandtatenda.studentlecturerplatform.model;

import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
import com.anesuandtatenda.studentlecturerplatform.web.requests.UserAccountRequest;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author GustTech
 */
@Entity
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Size(min = 1, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "year")
    private long year;

    @Size(max = 50)
    @Column(name = "reg_number", unique = true)
    private String regNumber;

    @Transient
    @ManyToOne
    private Programs program;

    private long programId;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "password")
    private String password;

    private String email;

    public UserAccount() {
    }

    public UserAccount(Integer id) {
        this.id = id;
    }

    public UserAccount(Integer id, String firstName, String lastName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

//	public Programs getProgram() {
//		return program;
//	}
//
//	public void setProgram(Programs program) {
//		this.program = program;
//	}

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserAccount fromCommand(UserAccountRequest request) {

        if (request == null) {
            return null;
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(request.getFirstName());
        userAccount.setLastName(request.getLastName());
        userAccount.setRegNumber(request.getRegNumber());
        userAccount.setProgramId(request.getProgramId());
        userAccount.setYear(request.getYear());
        userAccount.setPassword(request.getPassword());
        userAccount.setEmail(request.getEmail());
        userAccount.setRole(request.getRole());

        return userAccount;
    }

    public void update(UserAccount updateRequest) {
        this.setFirstName(updateRequest.getFirstName());
        this.setLastName(updateRequest.getLastName());
        this.setRegNumber(updateRequest.getRegNumber());
        this.setYear(updateRequest.getYear());
        this.setEmail(updateRequest.getEmail());
        this.setProgramId(updateRequest.getProgramId());
        this.setRole(updateRequest.getRole());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", year=" + year +
                ", regNumber='" + regNumber + '\'' +
                ", program=" + program +
                ", role=" + role +
                '}';
    }
}
