package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
class TimeSlotServiceImpl extends BaseServiceImpl<TimeSlots, TimeSlotsRequest, TimeSlotsRequest> implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository) {
        super(timeSlotRepository);
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    protected Class<TimeSlots> getEntityClass() {
        return TimeSlots.class;
    }

    @Override
    public TimeSlots create(TimeSlotsRequest request) {

        boolean detailsExists = timeSlotRepository.existsByName(request.getName());

        if (detailsExists) {
            throw new InvalidRequestException("TimeSlot with the same name already exists");
        }

        TimeSlots timeSlot = TimeSlots.fromCommand(request);

        return timeSlotRepository.save(timeSlot);
    }


    @Override
    public TimeSlots update(TimeSlotsRequest request) {

        boolean detailsExists = timeSlotRepository.existsByName(request.getName());

        if (!detailsExists) {
            throw new InvalidRequestException("TimeSlot not found");
        }

        TimeSlots timeSlot = findById(request.getId());

        timeSlot.update(request);

        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }
}

