package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ApproveAppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Appointments;

public interface AppointmentService extends BaseService<Appointments, AppointmentRequest, AppointmentRequest> {

    Appointments approveBooking(ApproveAppointmentRequest request);

}