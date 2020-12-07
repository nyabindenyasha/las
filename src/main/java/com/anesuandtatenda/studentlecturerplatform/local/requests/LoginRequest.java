package com.anesuandtatenda.studentlecturerplatform.local.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String regNumber;

    private String password;
}
