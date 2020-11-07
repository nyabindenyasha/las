package com.anesuandtatenda.studentlecturerplatform.web.requests;

import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DepartmentRequest {

    private String name;
    private String description;
    private Long facaulty;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getFacaulty() {
        return facaulty;
    }
}
