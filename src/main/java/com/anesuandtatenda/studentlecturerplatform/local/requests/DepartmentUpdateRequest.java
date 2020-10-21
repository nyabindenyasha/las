package com.anesuandtatenda.studentlecturerplatform.local.requests;
import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import lombok.Data;

@Data
public class DepartmentUpdateRequest {

    private long id;

    private String name;

    private String description;

    private long facaultlyId;

    private Facaulty facaulty;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getFacaultlyId() {
        return facaultlyId;
    }

    public void setFacaultlyId(long facaultlyId) {
        this.facaultlyId = facaultlyId;
    }

    public Facaulty getFacaulty() {
        return facaulty;
    }

    public void setFacaulty(Facaulty facaulty) {
        this.facaulty = facaulty;
    }
}
