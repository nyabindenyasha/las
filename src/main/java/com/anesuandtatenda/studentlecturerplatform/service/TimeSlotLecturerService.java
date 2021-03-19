package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotLecturerRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;

import java.util.List;

public interface TimeSlotLecturerService extends BaseService<TimeSlotLecturer, TimeSlotLecturerRequest, TimeSlotLecturerRequest> {

    List<TimeSlotLecturer> findByDayOfWeek(long dayOfWeek);

    List<TimeSlotLecturer> findByAccountId(long id);

    List<TimeSlotLecturer> findByDayOfWeekAndAccountId(long dayOfWeek, long id);

   // TimeSlotLecturer findByDayOfWeekAndAccountIdAAndTimeSlotsId(long dayOfWeek, long id, long timeSlotId);

}
