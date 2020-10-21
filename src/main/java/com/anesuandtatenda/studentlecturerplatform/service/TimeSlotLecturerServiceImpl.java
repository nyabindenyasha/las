package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotLecturerRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotLecturerRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.UserAccountRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;


@Service
public class TimeSlotLecturerServiceImpl extends BaseServiceImpl<TimeSlotLecturer, TimeSlotLecturerRequest, TimeSlotLecturerRequest> implements TimeSlotLecturerService {


    private final TimeSlotLecturerRepository timeSlotLecturerRepository;

    private final UserAccountRepository userAccountRepository;

    private final TimeSlotRepository timeSlotRepository;

    TimeSlotLecturerServiceImpl(TimeSlotLecturerRepository timeSlotLecturerRepository, UserAccountRepository userAccountRepository, TimeSlotRepository timeSlotRepository) {
        super(timeSlotLecturerRepository);
        this.timeSlotLecturerRepository = timeSlotLecturerRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    protected Class<TimeSlotLecturer> getEntityClass() {
        return TimeSlotLecturer.class;
    }

    @Override
    public TimeSlotLecturer create(TimeSlotLecturerRequest request) {

        TimeSlotLecturer timeSlotLecturer = new TimeSlotLecturer();

        timeSlotLecturer.setAccount(userAccountRepository.getOne(request.getLecturerId()));

        timeSlotLecturer.setTimeSlots(timeSlotRepository.getOne(request.getTimeSlotId()));

        timeSlotLecturer.setDayOfWeek(request.getDayOfWeek());

        return timeSlotLecturerRepository.save(timeSlotLecturer);
    }


    @Override
    public TimeSlotLecturer update(TimeSlotLecturerRequest request) {
        return null;
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
    public List<TimeSlotLecturer> findByDayOfWeek(int dayOfWeek) {
        return timeSlotLecturerRepository.findByDayOfWeek(dayOfWeek);
    }

    @Override
    public List<TimeSlotLecturer> findByAccountId(long id) {
        return timeSlotLecturerRepository.findByAccountId(id);
    }

    @Override
    public List<TimeSlotLecturer> findByDayOfWeekAndAccountId(int dayOfWeek, long id) {
        return timeSlotLecturerRepository.findByDayOfWeekAndAccountId(dayOfWeek, id);
    }
}
