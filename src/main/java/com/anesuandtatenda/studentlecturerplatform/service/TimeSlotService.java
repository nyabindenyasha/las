package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;

import java.time.LocalTime;

public interface TimeSlotService extends BaseService<TimeSlots, TimeSlotsRequest, TimeSlotsRequest> {

    TimeSlots findByStartTime(LocalTime startTime);

}
