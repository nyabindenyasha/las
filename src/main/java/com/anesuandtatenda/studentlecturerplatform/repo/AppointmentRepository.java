package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.Appointments;

import java.util.Collection;

public interface AppointmentRepository extends BaseRepository<Appointments> {

    Collection<Appointments> findByAppointmentWith(long id);

    Collection<Appointments> findByAppointmentBy(long id);

}