package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotLecturerRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class HelperService {

    private final TimeSlotLecturerRepository timeSlotLecturerRepository;

    private final TimeSlotRepository timeSlotRepository;

    HelperService(TimeSlotLecturerRepository timeSlotLecturerRepository, TimeSlotRepository timeSlotRepository) {
        this.timeSlotLecturerRepository = timeSlotLecturerRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public TimeSlots evaluateTimeSlotByTime(LocalTime time){

        if(time.isAfter(LocalTime.parse("08:00")) && time.isBefore(LocalTime.parse("10:00")))
            return timeSlotRepository.findByStartTime(LocalTime.parse("08:00"));

        else
            return timeSlotRepository.findByStartTime(LocalTime.parse("10:15"));
    }

}
