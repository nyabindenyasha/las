package com.anesuandtatenda.studentlecturerplatform.web.requests;

import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
import lombok.Data;

@Data
public class UserAccountRequest {

    private String firstName;
    private String lastName;
    private String regNumber;
    private long year;
    private long programId;
    private Role role;
    private String password;
    private String email;

}
