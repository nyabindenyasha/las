package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotLecturerRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;
import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
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

        System.out.println(request);

        TimeSlotLecturer timeSlotLecturer = new TimeSlotLecturer();

        if (userAccountRepository.getOne(request.getLecturerId()).getRole() != Role.LECTURER)
            throw new InvalidRequestException("A valid lecturer's account must be provided!");

        timeSlotLecturer.setLecturerId(request.getLecturerId());

        timeSlotLecturer.setTimeSlotId(request.getTimeSlotId());

        List<TimeSlotLecturer> alreadyExisting = timeSlotLecturerRepository.findByDayOfWeekAndLecturerId(request.getDayOfWeek(), request.getLecturerId());

        timeSlotLecturer.setDayOfWeek(request.getDayOfWeek());

        if (alreadyExisting.stream().anyMatch(timeSlotLecturerAlreadyExisting -> {
            return timeSlotLecturerAlreadyExisting.getTimeSlotId() == request.getTimeSlotId();
        }))
            throw new InvalidRequestException("Lecturer already added to that time slot");
        else
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
    public List<TimeSlotLecturer> findByDayOfWeek(long dayOfWeek) {
        return timeSlotLecturerRepository.findByDayOfWeek(dayOfWeek);
    }

    @Override
    public List<TimeSlotLecturer> findByAccountId(long id) {
        return timeSlotLecturerRepository.findByLecturerId(id);
    }

    @Override
    public List<TimeSlotLecturer> findByDayOfWeekAndAccountId(long dayOfWeek, long id) {
        return timeSlotLecturerRepository.findByDayOfWeekAndLecturerId(dayOfWeek, id);
    }

//    @Override
//    public TimeSlotLecturer findByDayOfWeekAndAccountIdAAndTimeSlotsId(long dayOfWeek, long id, long timeSlotId) {
//        return timeSlotLecturerRepository.findByDayOfWeekAndAccountIdAAndTimeSlotsId(dayOfWeek, id, timeSlotId);
//    }
}
