package com.anesuandtatenda.studentlecturerplatform.web.requests;

import lombok.Data;

@Data
public class DepartmentRequest {

    private String name;
    private String description;
    private long facaultyId;

}
