package com.anesuandtatenda.studentlecturerplatform.web.requests;

import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;


public class AccountRequest {

    private String firstName;
    private String lastName;
    private String username;
    private int year;
    private long programId;
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

    public Long getProgram() {
        return programId;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
