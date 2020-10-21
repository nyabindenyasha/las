package com.anesuandtatenda.studentlecturerplatform.repo;


import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;

public interface FacaultyRepository extends BaseRepository<Facaulty> {

    boolean existsByName(String name);

}
