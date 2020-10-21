package com.anesuandtatenda.studentlecturerplatform.local.requests;

import com.anesuandtatenda.studentlecturerplatform.model.Department;
import lombok.Data;

@Data
public class ProgramCreateRequest {

    private String name;

    private String description;

    private long departmentId;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }


}
