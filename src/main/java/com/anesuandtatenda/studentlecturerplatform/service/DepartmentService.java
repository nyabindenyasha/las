package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.local.requests.DepartmentUpdateRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Department;
import com.anesuandtatenda.studentlecturerplatform.web.requests.DepartmentRequest;

public interface DepartmentService extends BaseService<Department, DepartmentRequest, DepartmentUpdateRequest> {

    boolean existsByName(String name);

    Department getByName(String name);

}
