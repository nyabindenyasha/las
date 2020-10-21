package com.anesuandtatenda.studentlecturerplatform.local.requests;

import lombok.Data;

@Data
public class RoleUpdateRequest {

    private long id;

    private String name;

    private String description;

}
