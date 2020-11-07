package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public class FacultyServiceImpl implements FacultyService{

    @Override
    public Facaulty findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<Facaulty> findAll(Pageable pageable, String searchQuery) {
        return null;
    }

    @Override
    public Facaulty create(Facaulty createDto) {
        return null;
    }

    @Override
    public Facaulty update(Facaulty updateDto) {
        return null;
    }

    @Override
    public Collection<Facaulty> findAll() {
        return null;
    }
}
