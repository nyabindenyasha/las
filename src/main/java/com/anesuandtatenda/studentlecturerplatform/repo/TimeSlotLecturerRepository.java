package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;

import java.util.List;

public interface TimeSlotLecturerRepository extends BaseRepository<TimeSlotLecturer> {

    List<TimeSlotLecturer> findByDayOfWeek(int dayOfWeek);

    List<TimeSlotLecturer> findByAccountId(long id);

    List<TimeSlotLecturer> findByDayOfWeekAndAccountId(int dayOfWeek, long id);

  //  TimeSlotLecturer findByDayOfWeekAndAccountIdAAndTimeSlotsId(int dayOfWeek, long id, long timeSlotId);
}
