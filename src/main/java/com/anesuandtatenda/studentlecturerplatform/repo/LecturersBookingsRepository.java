package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;

import java.time.LocalDate;
import java.util.List;

public interface LecturersBookingsRepository extends BaseRepository<LecturersBookings> {

    List<LecturersBookings> findByDate(LocalDate date);

    List<LecturersBookings> findByLecturer(long id);

    List<LecturersBookings> findByDateAndLecturer(LocalDate date, long id);

}
