package com.anesuandtatenda.studentlecturerplatform.web.requests;

import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DepartmentRequest {

    private String name;
    private String description;
    private long facaultyId;

}
