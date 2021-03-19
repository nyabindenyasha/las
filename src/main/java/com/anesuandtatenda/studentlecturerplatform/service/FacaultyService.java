package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;

public interface FacaultyService extends BaseService<Facaulty, Facaulty, Facaulty> {

    boolean existsByName(String name);

    Facaulty getByName(String name);

}
