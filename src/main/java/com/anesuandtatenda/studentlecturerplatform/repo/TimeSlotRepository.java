package com.anesuandtatenda.studentlecturerplatform.repo;


import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;

import java.time.LocalTime;

public interface TimeSlotRepository extends BaseRepository<TimeSlots> {

    boolean existsByName(String name);

    boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

    TimeSlots findByStartTime(LocalTime startTime);

}
