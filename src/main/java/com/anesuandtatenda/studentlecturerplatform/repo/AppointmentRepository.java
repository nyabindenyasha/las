package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.Appointments;

import java.util.Collection;
import java.util.Date;

public interface AppointmentRepository extends BaseRepository<Appointments> {

    Collection<Appointments> findByAppointmentWith(long id);

    Collection<Appointments> findByAppointmentBy(long id);

    Appointments findByDate(Date date);

    boolean existsByDate(Date date);

//    Optional<Appointments> findByDateUuid(long uuid);

}