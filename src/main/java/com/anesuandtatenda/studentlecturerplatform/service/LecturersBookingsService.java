package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;

import java.time.LocalDate;
import java.util.List;

public interface LecturersBookingsService extends BaseService<LecturersBookings, LecturersBookings, LecturersBookings> {

    List<LecturersBookings> findByDate(LocalDate date);

    List<LecturersBookings> findBylecturerId(long id);

 //   List<LecturersBookings> findBylecturerIdAndDate(long id, LocalDate date);

    List<LecturersBookings> findByDateAndLecturerId(LocalDate date, long id);

}
