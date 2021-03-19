package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramCreateRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramUpdateRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Department;
import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import com.anesuandtatenda.studentlecturerplatform.model.Programs;
import com.anesuandtatenda.studentlecturerplatform.repo.DepartmentRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.FacaultyRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.ProgramRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class ProgramServiceImpl extends BaseServiceImpl<Programs, ProgramCreateRequest, ProgramUpdateRequest> implements ProgramService {

    private final ProgramRepository programRepository;

    private final DepartmentRepository departmentRepository;

    private final FacaultyRepository facaultyRepository;

    ProgramServiceImpl(ProgramRepository programRepository, DepartmentRepository departmentRepository, FacaultyRepository facaultyRepository) {
        super(programRepository);
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
        this.facaultyRepository = facaultyRepository;
    }

    @Override
    protected Class<Programs> getEntityClass() {
        return Programs.class;
    }

    @Override
    public Programs create(ProgramCreateRequest request) {

        boolean detailsExists = programRepository.existsByName(request.getName());

        if (detailsExists) {
            throw new InvalidRequestException("Program with the same name already exists");
        }

        Optional<Department> department = departmentRepository.findById(request.getDepartmentId());
        Programs program = Programs.fromCommand(request);
        program.setDepartment(department.get());
        return programRepository.save(program);
    }


    @Override
    public Programs update(ProgramUpdateRequest request) {

        boolean detailsExists = programRepository.existsByName(request.getName());

        if (!detailsExists) {
            throw new InvalidRequestException("Program not found");
        }

        Optional<Department> department = departmentRepository.findById(request.getDepartmentId());

        Optional<Facaulty> facaulty = facaultyRepository.findById(department.get().getFacaulty().getId());

        department.get().setFacaulty(facaulty.get());

        request.setDepartment(department.get());

        Programs program = findById(request.getId());

        program.update(request);

        return programRepository.save(program);
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }

    @Override
    public boolean existsByName(String name) {
        return programRepository.existsByName(name);
    }

    @Override
    public Programs getByName(String name) {
        return programRepository.getByName(name);
    }
}
