package com.anesuandtatenda.studentlecturerplatform.repo;


import com.anesuandtatenda.studentlecturerplatform.model.Department;

public interface DepartmentRepository extends BaseRepository<Department> {

    boolean existsByName(String name);

}
