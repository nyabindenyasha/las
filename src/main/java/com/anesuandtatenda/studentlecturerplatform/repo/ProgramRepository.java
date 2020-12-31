package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.Programs;

public interface ProgramRepository extends BaseRepository<Programs> {

    boolean existsByName(String name);

    Programs getByName(String name);
}
