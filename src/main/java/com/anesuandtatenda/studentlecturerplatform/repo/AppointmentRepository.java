package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.Appointments;

import java.util.Collection;

public interface AppointmentRepository extends BaseRepository<Appointments> {

    Collection<Appointments> findByAppointmentWithId(long id);

    Collection<Appointments> findByAppointmentById(long id);

}