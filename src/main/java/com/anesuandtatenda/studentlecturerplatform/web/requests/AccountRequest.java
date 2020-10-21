package com.anesuandtatenda.studentlecturerplatform.web.requests;

import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;

import java.sql.Date;

public class AccountRequest {

    private String firstName;
    private String lastName;
    private String username;
    private int year;
    private String regNumber;
    private long program;
    private Role role;
    private String password;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getYear() {
        return year;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public Long getProgram() {
        return program;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
