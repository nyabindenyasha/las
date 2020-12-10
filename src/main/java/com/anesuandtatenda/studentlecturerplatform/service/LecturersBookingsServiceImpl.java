package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;
import com.anesuandtatenda.studentlecturerplatform.repo.LecturersBookingsRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

@Service
public class LecturersBookingsServiceImpl extends BaseServiceImpl<LecturersBookings, LecturersBookings, LecturersBookings> implements LecturersBookingsService {

    private final LecturersBookingsRepository lecturersBookingsRepository;

    LecturersBookingsServiceImpl(LecturersBookingsRepository lecturersBookingsRepository) {
        super(lecturersBookingsRepository);
        this.lecturersBookingsRepository = lecturersBookingsRepository;
    }

    @Override
    protected Class<LecturersBookings> getEntityClass() {
        return LecturersBookings.class;
    }

    @Override
    public LecturersBookings create(LecturersBookings request) {

        LecturersBookings lecturersBookings = LecturersBookings.fromCommand(request);

        return lecturersBookingsRepository.save(lecturersBookings);
    }


    @Override
    public LecturersBookings update(LecturersBookings request) {

        LecturersBookings lecturersBookings = findById(request.getId());

        lecturersBookings.update(request);

        return lecturersBookingsRepository.save(lecturersBookings);
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
    public List<LecturersBookings> findByDate(LocalDate date) {
        return lecturersBookingsRepository.findByDate(date);
    }

    @Override
    public List<LecturersBookings> findBylecturerId(long id) {
        return lecturersBookingsRepository.findByLecturer(id);
    }

    @Override
    public List<LecturersBookings> findByDateAndLecturerId(LocalDate date, long id) {
        return lecturersBookingsRepository.findByDateAndLecturer(date, id);
    }


}
