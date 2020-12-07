package com.anesuandtatenda.studentlecturerplatform.web.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String regNumber;
    private String password;

}
