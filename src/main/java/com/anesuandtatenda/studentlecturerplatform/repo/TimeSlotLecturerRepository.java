package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;

import java.util.List;

public interface TimeSlotLecturerRepository extends BaseRepository<TimeSlotLecturer> {

    List<TimeSlotLecturer> findByDayOfWeek(long dayOfWeek);

    List<TimeSlotLecturer> findByLecturerId(long id);

    List<TimeSlotLecturer> findByDayOfWeekAndLecturerId(long dayOfWeek, long id);

  //  TimeSlotLecturer findByDayOfWeekAndAccountIdAAndTimeSlotsId(long dayOfWeek, long id, long timeSlotId);
}
