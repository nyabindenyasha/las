package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequestMobile;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ApproveAppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Appointments;

import java.util.Collection;
import java.util.Date;

public interface AppointmentService extends BaseService<Appointments, AppointmentRequest, AppointmentRequest> {

    Appointments approveBooking(ApproveAppointmentRequest request);

    Appointments createFromMobile(AppointmentRequestMobile request);

    Collection<Appointments> findByAppointmentWithId(long id);

    Collection<Appointments> findByAppointmentById(long id);

    boolean existsByDate(Date date);

}
