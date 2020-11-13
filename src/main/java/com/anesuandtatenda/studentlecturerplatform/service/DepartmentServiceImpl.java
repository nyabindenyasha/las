package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.DepartmentCreateRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.DepartmentUpdateRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Department;
import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import com.anesuandtatenda.studentlecturerplatform.repo.DepartmentRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.FacaultyRepository;
import com.anesuandtatenda.studentlecturerplatform.web.requests.DepartmentRequest;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@Service
class DepartmentServiceImpl extends BaseServiceImpl<Department, DepartmentRequest, DepartmentUpdateRequest> implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final FacaultyRepository facaultyRepository;

    DepartmentServiceImpl(DepartmentRepository departmentRepository, FacaultyRepository facaultyRepository) {
        super(departmentRepository);
        this.departmentRepository = departmentRepository;
        this.facaultyRepository = facaultyRepository;
    }

    @Override
    protected Class<Department> getEntityClass() {
        return Department.class;
    }

    @Override
    public Department create(DepartmentRequest request) {

        boolean detailsExists = departmentRepository.existsByName(request.getName());

        if (detailsExists) {
            throw new InvalidRequestException("Department with the same name already exists");
        }
        Optional<Facaulty> facaulty = facaultyRepository.findById(request.getFacaultyId());
        Department department=Department.fromCommand(request);
        department.setFacaulty(facaulty.get());
        return departmentRepository.save(department);
    }


    @Override
    public Department update(DepartmentUpdateRequest request) {

        boolean detailsExists = departmentRepository.existsByName(request.getName());

        if (!detailsExists) {
            throw new InvalidRequestException("Department not found");
        }

        Department department = findById(request.getId());

        Optional<Facaulty> facaulty = facaultyRepository.findById(request.getFacaultlyId());

        request.setFacaulty(facaulty.get());

        department.update(request);

        return departmentRepository.save(department);
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }

}

