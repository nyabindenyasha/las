package com.anesuandtatenda.studentlecturerplatform.local.requests;

import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import lombok.Data;

@Data
public class DepartmentCreateRequest {

    private String name;

    private String description;

    private Integer facaultlyId;


}
