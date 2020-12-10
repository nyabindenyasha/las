package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Spaces;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;
import com.anesuandtatenda.studentlecturerplatform.repo.SpacesRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotLecturerRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Collection;

@Service
public class SpacesServiceImpl extends BaseServiceImpl<Spaces, Spaces, Spaces> implements SpacesService {

    private final SpacesRepository spacesRepository;

    private final TimeSlotLecturerRepository timeSlotLecturerRepository;

    SpacesServiceImpl(SpacesRepository spacesRepository, TimeSlotLecturerRepository timeSlotLecturerRepository) {
        super(spacesRepository);
        this.spacesRepository = spacesRepository;
        this.timeSlotLecturerRepository =timeSlotLecturerRepository;
    }

    @Override
    protected Class<Spaces> getEntityClass() {
        return Spaces.class;
    }

    @Override
    public Spaces create(Spaces request) {

        return spacesRepository.save(request);
    }


    @Override
    public Spaces update(Spaces request) {

        Spaces spaces = findById(request.getId());

        return spacesRepository.save(spaces);
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
    public Collection<Spaces> findByLecturerId(long lecturerId) {
        return spacesRepository.findByLecturerId(lecturerId);
    }

    @Override
    public Collection<Spaces> evaluateByLecturerId(long lecturerId, int dayOfWeek) {

        Collection<TimeSlotLecturer> timeSlotLecturer = timeSlotLecturerRepository.findByDayOfWeekAndLecturerId(dayOfWeek, lecturerId);

        return null;
    }

}

