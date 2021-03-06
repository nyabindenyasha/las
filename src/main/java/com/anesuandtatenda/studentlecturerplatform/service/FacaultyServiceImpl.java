package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import com.anesuandtatenda.studentlecturerplatform.repo.FacaultyRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
class FacaultyServiceImpl extends BaseServiceImpl<Facaulty, Facaulty, Facaulty> implements FacaultyService {

    private final FacaultyRepository facaultyRepository;

    FacaultyServiceImpl(FacaultyRepository facaultyRepository) {
        super(facaultyRepository);
        this.facaultyRepository = facaultyRepository;
    }

    @Override
    protected Class<Facaulty> getEntityClass() {
        return Facaulty.class;
    }

    @Override
    public Facaulty create(Facaulty request) {

        boolean detailsExists = facaultyRepository.existsByName(request.getName());

        if (detailsExists) {
            throw new InvalidRequestException("Facaulty with the same name already exists");
        }

        Facaulty role = Facaulty.fromCommand(request);

        return facaultyRepository.save(role);
    }


    @Override
    public Facaulty update(Facaulty request) {

        Facaulty facaulty = findById(request.getId());

        facaulty.update(request);

        return facaultyRepository.save(facaulty);
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
        return facaultyRepository.existsByName(name);
    }

    @Override
    public Facaulty getByName(String name) {
        return facaultyRepository.getByName(name);
    }
}