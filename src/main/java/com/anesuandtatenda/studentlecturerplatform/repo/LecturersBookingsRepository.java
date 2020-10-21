package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;

import java.time.LocalDate;
import java.util.List;

public interface LecturersBookingsRepository extends BaseRepository<LecturersBookings> {

    List<LecturersBookings> findByDate(LocalDate date);

    List<LecturersBookings> findByLecturerId(long id);

    List<LecturersBookings> findByDateAndLecturerId(LocalDate date, long id);

}
